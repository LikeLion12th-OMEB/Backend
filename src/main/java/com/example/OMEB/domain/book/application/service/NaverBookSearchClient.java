package com.example.OMEB.domain.book.application.service;

import com.example.OMEB.domain.book.presentation.dto.NaverBookDTO;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:security/application-naver.yml")
@Slf4j
public class NaverBookSearchClient {

    private  final WebClient webClient;

    private final String clientId;
    private final String clientSecret;
    private final String baseUrl;

    private static final int DISPLAY_BOOKS_SIZE = 10;

    @Autowired
    public NaverBookSearchClient(WebClient.Builder webClientBuilder,
                                 @Value("${openapi.security.naver.client-id}") String clientId,
                                 @Value("${openapi.security.naver.client-secret}") String clientSecret,
                                 @Value("${openapi.security.naver.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl)
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.baseUrl = baseUrl;
        log.info("NaverBookSearchClient created with clientId: {}, clientSecret: {}, baseUrl: {}", clientId, clientSecret, baseUrl);
    }

    public List<NaverBookDTO> searchBooks(String title, String isbn) {
        String response = this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/book_adv.xml")
                        .queryParam("d_titl", title != null ? title : "")
                        .queryParam("d_isbn", isbn != null ? isbn : "")
                        .queryParam("display", DISPLAY_BOOKS_SIZE)
                        .queryParam("start", 1)
                        .queryParam("sort", "date")
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();
        if (response == null) {
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        List<NaverBookDTO> naverBookDTOS = parseBooks(response);
        return parseBooks(response);
    }

    private List<NaverBookDTO> parseBooks(String xmlResponse) {
        List<NaverBookDTO> naverBookDTOS = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));

            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                Node node = itemList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String title = getTagValue("title", element);
                    String link = getTagValue("link", element);
                    String image = getTagValue("image", element);
                    String author = getTagValue("author", element);
                    String discount = getTagValue("discount", element, ""); // Default to empty string if not present
                    String publisher = getTagValue("publisher", element);
                    String isbn = getTagValue("isbn", element);
                    String description = getTagValue("description", element);
                    String pubdate = getTagValue("pubdate", element);

                    NaverBookDTO naverBookDTO = new NaverBookDTO(title, link, image, author, discount, publisher, isbn, description, pubdate);
                    naverBookDTOS.add(naverBookDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return naverBookDTOS;
    }

    private String getTagValue(String tag, Element element) {
        return getTagValue(tag, element, null);
    }

    private String getTagValue(String tag, Element element, String defaultValue) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                NodeList childNodes = node.getChildNodes();
                if (childNodes.getLength() > 0) {
                    Node childNode = childNodes.item(0);
                    if (childNode != null) {
                        return childNode.getNodeValue();
                    }
                }
            }
        }
        return defaultValue;
    }


}