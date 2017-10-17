package com.goranzuri.anime.service;

import com.goranzuri.anime.AnimeApiConfiguration;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.AnimeNotFoundException;
import com.goranzuri.anime.providers.DbProvider;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.lang.model.util.Elements;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gzuri on 15/10/2017.
 */
public class AniDbService {



    @Inject
    public AniDbService(){
    }


    public static String prepareStringForComparison(String animeTitle){
        animeTitle = animeTitle.replaceAll("[-+.^:,_ ]","");
        animeTitle = animeTitle.toLowerCase();

        return animeTitle;
    }

    //TODO move to AnidbService
    public static Map<String, Integer> prepareListOfTitlesForComparation(Map<String, Integer> animeList){
        Map<String, Integer> cleanedAnimeList = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> item : animeList.entrySet()){
            cleanedAnimeList.put(prepareStringForComparison(item.getKey()), item.getValue());
        }
        return  cleanedAnimeList;
    }
    //TODO move to AnidbService
    public Integer getAnidbCode(String animeName) throws AnimeNotFoundException {
        try {

            InputStream apiSearchResults = getResultsFromApi(animeName);

            Map<String, Integer> serviceResponseParsed = parseXmlResults(apiSearchResults);

            Map<String, Integer> animeTitlesWithId = prepareListOfTitlesForComparation(serviceResponseParsed);

            animeName = prepareStringForComparison(animeName);

            if (animeTitlesWithId.containsKey(animeName))
                return animeTitlesWithId.get(animeName);

            //animeTitlesWithId.get(animeName);
        } catch (Exception ex){

        }
        throw  new AnimeNotFoundException("With name: " + animeName);
    }



    private InputStream getResultsFromApi(String animeName) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://anisearch.outrance.pl/index.php?task=search&langs=en&query=" + URLEncoder.encode(animeName, "UTF-8"));

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return conn.getInputStream();
    }

    private Map<String, Integer> parseXmlResults(InputStream xmlResult) throws ParserConfigurationException, IOException, SAXException {
        Map<String, Integer> animeList = new HashMap<String, Integer>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //Get the DOM Builder
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Load and Parse the XML document
        //document contains the complete XML as a Tree.
        Document document = builder.parse(xmlResult);

        //Iterating through the nodes and extracting the data.
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {

            //We have encountered an <employee> tag.
            Node node = nodeList.item(i);
            if (node instanceof Element) {

                NodeList childNodes = node.getChildNodes();
                for(int j = 0; j < childNodes.getLength(); j++){
                    Node childNode = childNodes.item(j);
                    if (childNode.getAttributes().getNamedItem("lang").getNodeValue().equals("en")
                            && (childNode.getAttributes().getNamedItem("type").getNodeValue().equals("official")
                            || childNode.getAttributes().getNamedItem("type").getNodeValue().equals("main")
                            || childNode.getAttributes().getNamedItem("type").getNodeValue().equals("syn"))){

                        Integer aid = Integer.parseInt(node.getAttributes().getNamedItem("aid").getNodeValue());

                        if (!animeList.containsKey(childNode.getTextContent()))
                            animeList.put(childNode.getTextContent(), aid);
                    }
                }
            }

        }

        return animeList;
    }


    public String getThumbnailUrl(String aniDbCode) throws IOException {
        Connection connection = Jsoup.connect("https://anidb.net/perl-bin/animedb.pl?show=anime&aid=" + aniDbCode);
        connection.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        org.jsoup.nodes.Document doc = connection.get();
        org.jsoup.select.Elements allImages = doc.select("img");
        //org.jsoup.select.Elements allImages = mainLayout.select("img");


        List<String> mainImages = allImages.stream()
                .filter(x -> x.attr("src") != null && !x.attr("src").contains("thumb") && !x.attr("src").contains("icons"))
                .map(x -> x.attr("src"))
                .collect(Collectors.toList());

        return mainImages.get(0);
    }
}
