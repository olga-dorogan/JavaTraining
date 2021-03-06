package com.custom.servlet;

import com.custom.domain.ParfumWithPrice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.custom.service.OfyService.ofy;

/**
 * Created by olga on 21.06.15.
 */
public class ParfumsPricesLoader extends HttpServlet {
    Logger LOG = Logger.getLogger(ParfumWithPrice.class.getName());

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String PAGE_ADDRESS = "http://www.parfumaniya.com.ua/informer/20";
    private static final String PRICE_TEXT = "грн/мл";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Beginning of parfums page loading and parsing");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(PAGE_ADDRESS).openStream(), DEFAULT_CHARSET))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            loadParfumEntities(sb.toString());
            resp.setStatus(204);
            return;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    private List<ParfumWithPrice> loadParfumEntities(String content) {
        List<ParfumWithPrice> parfumWithPrices = new ArrayList<>();
        Document document = Jsoup.parse(content);
        Elements elementsByTagTd = document.body().getElementsByTag("td");
        // TODO: do more intellectual switching between fields
        String name, author, price;
        for (Element td : elementsByTagTd) {
            name = "";
            author = "";
            Elements elementsByTagA = td.getElementsByTag("a");
            for (Element link : elementsByTagA) {
                if (link.hasText()) {
                    if (name.equals("")) {
                        name = link.text();
                    } else {
                        author = link.text();
                    }
                }
            }
            Elements elementsContainingPrice = td.getElementsContainingOwnText(PRICE_TEXT);
            price = elementsContainingPrice.text().replaceFirst(author, "");
            Double priceAsDouble = 0.0;
            for (String pricePart : price.split(" ")) {
                try {
                    priceAsDouble = Double.valueOf(pricePart);
                    break;
                } catch (NumberFormatException ignore) {
                }
            }
            parfumWithPrices.add(new ParfumWithPrice(name, author, priceAsDouble));
        }
        for (ParfumWithPrice parfum : parfumWithPrices) {
            ofy().save().entity(parfum).now();
        }
        return parfumWithPrices;
    }
}
