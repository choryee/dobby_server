package com.emgram.kr.dobby.utils;

import com.emgram.kr.dobby.dto.caldav.CaldavAuth;
import com.emgram.kr.dobby.dto.caldav.CaldavRequestData;
import okhttp3.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class CaldavNetwork {

    public static CaldavDocument request(CaldavAuth auth, String url, CaldavRequestData data){
        return CaldavNetwork.request(auth, url, data, 0);
    }

    public static CaldavDocument request(CaldavAuth auth, String url, CaldavRequestData data, int depth){
        return CaldavNetwork.request(auth, url, data, depth, "PROPFIND");
    }

    public static CaldavDocument request(CaldavAuth auth, String url, CaldavRequestData data, int depth, String method){
        OkHttpClient client = new OkHttpClient();
        String body = data.getRequestData();
        Request req = new Request.Builder()
                .url(url)
                .method(method, RequestBody.create(MediaType.parse(body), body))
                .header("DEPTH", String.valueOf(depth))
                .header("Authorization", auth.getCredential())
                .header("Content-Type", "text/xml")
                .build();

        Response res = null;
        String xmlString = null;
        Document document = null;

        try{
            res = client.newCall(req).execute();
            xmlString = res.body().string();
            document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xmlString)));
        } catch(SAXException e){
            throw new RuntimeException(e);
        } catch(IOException e){
            throw new RuntimeException(e);
        } catch(ParserConfigurationException e){
            throw new RuntimeException(e);
        }

        Element element = document.getDocumentElement();
        CaldavDocument nodes = new CaldavDocument(element.getChildNodes());
        return nodes;
    }
}
