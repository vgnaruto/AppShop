package com.example.windows10.dapurukm;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XMLParser {

//    public ArrayList<Provinsi> parseXML() {
//        XmlPullParserFactory parserFactory;
//        try {
//            parserFactory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = parserFactory.newPullParser();
//            InputStream input = MainActivity.getInstance().getAssets().open("database.xml");
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            parser.setInput(input, null);
//            return processParsing(parser);
//        } catch (XmlPullParserException e) {
//        } catch (IOException e) {
//        }
//        return null;
//    }
//    private ArrayList<Provinsi> processParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
//        ArrayList<Provinsi> provinsis = new ArrayList<>();
//        Provinsi cProvinsi = new Provinsi();
//
//        ArrayList<Kabupaten> listKabupaten = new ArrayList<>();
//        Kabupaten cKabupaten = new Kabupaten();
//
//        ArrayList<Kecamatan> listKecamatan = new ArrayList<>();
//
//        int eventType = parser.getEventType();
//        while(eventType != XmlPullParser.END_DOCUMENT){
//            String tokenName = null;
//            switch (eventType) {
//                case XmlPullParser.START_TAG:
//                    tokenName = parser.getName();
//                    if ("Provinsi".equalsIgnoreCase(tokenName)) {
//                        cProvinsi = new Provinsi();
//                        provinsis.add(cProvinsi);
//                    } else if ("nama_provinsi".equalsIgnoreCase(tokenName)) {
//                        cProvinsi.setNamaProvinsi(parser.nextText());
//                        listKabupaten = new ArrayList<>();
//                        cProvinsi.setListKabupaten(listKabupaten);
//                    } else if ("Kabupaten".equalsIgnoreCase(tokenName)) {
//                        cKabupaten = new Kabupaten();
//                        listKabupaten.add(cKabupaten);
//                    } else if ("nama_kabupaten".equalsIgnoreCase(tokenName)) {
//                        cKabupaten.setNamaKabupaten(parser.nextText().trim());
//                    } else if ("Kecamatan".equalsIgnoreCase(tokenName)) {
//                        listKecamatan = new ArrayList<>();
//                        cKabupaten.setListKecamatan(listKecamatan);
//                    } else if ("nama_kecamatan".equalsIgnoreCase(tokenName)) {
//                        String[] token = parser.nextText().split(",");
//                        for (String t : token) {
//                            listKecamatan.add(new Kecamatan(t.trim()));
//                        }
//                    }
//                    break;
//            }
//            eventType = parser.next();
//        }
//        return provinsis;
//    }
}