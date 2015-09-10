package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Entity
class Booking {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (id != null ? !id.equals(booking.id) : booking.id != null) return false;
        return !(name != null ? !name.equals(booking.name) : booking.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

interface BookingRepository extends JpaRepository<Booking, Long> {
    Collection<Booking> findByName(String name);
}

@Component
class BookingCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        List<Booking> bookingList = bookingRepository.findAll();
        for (Booking booking : bookingList) {
            System.out.println(booking);
        }
    }

    @Autowired
    BookingRepository bookingRepository;
}

@RestController
class BookingRestController {

    @RequestMapping("/bookings")
    Collection<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    @Autowired
    BookingRepository bookingRepository;
}