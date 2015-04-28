package com.custom.service.rs;

import com.custom.service.StudentVO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 27.04.15.
 */
@XmlRootElement
public class RSStudentVOWithLinks {

    private StudentVO studentVO;
    private List<Link> links;

    public RSStudentVOWithLinks() {

    }

    public RSStudentVOWithLinks(StudentVO studentVO, String urlPrefix) {
        this.studentVO = studentVO;
        links = new ArrayList<>();
        StringBuilder sbList = new StringBuilder();
        try {
            sbList.append(urlPrefix)
                    .append("?")
                    .append("group").append("=").append(URLEncoder.encode(studentVO.getGroup(), "UTF-8"))
                    .append("&")
                    .append("course").append("=").append(studentVO.getCourse());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        links.add(new Link("self", urlPrefix + "/" + studentVO.getId(), "GET"));
        links.add(new Link("update", urlPrefix + "/" + studentVO.getId(), "PUT"));
        links.add(new Link("delete", urlPrefix + "/" + studentVO.getId(), "DELETE"));
        links.add(new Link("add", urlPrefix, "POST"));
        links.add(new Link("list", sbList.toString(), "GET"));
    }


    @XmlRootElement
    public static class Link {
        private String rel;
        private String href;
        private String method;

        public Link() {

        }

        public Link(String rel, String href, String method) {
            this.rel = rel;
            this.href = href;
            this.method = method;
        }

        public String getRel() {
            return rel;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }

    public StudentVO getStudentVO() {
        return studentVO;
    }

    @XmlElementWrapper
    @XmlElement(name = "link")
    public List<Link> getLinks() {
        return links;
    }

    public void setStudentVO(StudentVO studentVO) {
        this.studentVO = studentVO;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
