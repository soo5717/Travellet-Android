package com.example.travellet.feature.plan.AddPlace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.travellet.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class AddPlaceActivity extends AppCompatActivity {

    //리사이클러뷰에 필요한 변수
    ArrayList<Integer> placeID = new ArrayList<>();
    RecyclerView addPlaceRecyclerView;
    AddPlaceAdapter addPlaceAdapter;
    ArrayList<AddPlaceItem> addPlaceItems = new ArrayList<>();
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        initToolbar();

        addPlaceRecyclerView = findViewById(R.id.recyclerview_add_place);
        //리사이클러뷰에 adapter 객체 지정
        addPlaceAdapter = new AddPlaceAdapter(addPlaceItems);
        addPlaceRecyclerView.setAdapter(addPlaceAdapter);
        layoutManager = new LinearLayoutManager(getApplicationContext()); // 리사이클러뷰 그리드 레이아웃
        addPlaceRecyclerView.setLayoutManager(layoutManager);

        //리사이클러뷰 어댑터 클릭 이벤트
        addPlaceAdapter.setOnItemClickListener(
                (v, position) -> {
                    Intent intent=new Intent();
                    intent.putExtra("title", addPlaceItems.get(position).getTitle());
                    intent.putExtra("x", addPlaceItems.get(position).getMapx());
                    intent.putExtra("y", addPlaceItems.get(position).getMapy());
                    setResult(RESULT_OK, intent);
                    finish();
                }
        );
    }

    //툴바 초기화 메소드
    private void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_add_place);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_backspace_24dp);
        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.place_add_search);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                addPlaceItems.clear();
                placeID.clear();
                //requestReadLike();
                getPlaceListData(keyword);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //장소 어댑터 아이템 추가
    public void addItem(String title, String type, String addr, double x, double y) {
        AddPlaceItem item = new AddPlaceItem(title, type, addr, x, y);
        item.setTitle(title);
        item.setType(type);
        item.setAddr(addr);
        item.setMapx(x);
        item.setMapy(y);
        addPlaceItems.add(item);
    }

    //장소 검색 결과 리스트를 보여주는 메소드
    public void getPlaceListData(String keyword) {
        try {
            String serviceKey = "x%2FB48ucBtE1tDbI%2FOOc%2B0Qh3MP%2BlYEETjSL5Q8G0L912refn%2FEii%2FgZ5E0S%2Bdqs%2BAmxagAo%2B9%2BieRWWN80QxNA%3D%3D";

            String urlBuilder1 = "http://api.visitkorea.or.kr/openapi/service/rest/EngService/searchKeyword" + "?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey + /*Service Key*/
                    "&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8") + /*공공데이터포털에서 발급받은 인증키*/
                    "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("500", "UTF-8") + /*한 페이지 결과 수*/
                    "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + /*현재 페이지 번호*/
                    "&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("AND", "UTF-8") + /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
                    "&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("Travellet", "UTF-8") + /*서비스명=어플명*/
                    "&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8") + /*목록 구분(Y=목록, N=개수)*/
                    "&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("B", "UTF-8") + /*(A=제목순,B=조회순,C=수정일순,D=생성일순) 대표이미지가 반드시 있는 정렬(O=제목순, P=조회순, Q=수정일순, R=생성일순)*/
                    "&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + /*지역코드*/
                    "&" + URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8");/*URL*//*검색 요청할 키워드(국문=인코딩 필요)*/
            URL url = new URL(urlBuilder1);

            ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conManager.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {
                new DownloadXml().execute(url.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error", "SearchKeyword에러");
        }
    }

    //xml 형식인 검색 결과를 파싱해주는 메소드
    private class DownloadXml extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            Document doc = null;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("error", String.valueOf(e));
                    }
                }, 0);
            }
            return doc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Document doc) {
            String id = "", title = "", sigungu = "", mapx = "", mapy = "", type = "";
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                String thumbnail = " ";
                Node node = nodeList.item(i);
                Element element = (Element) node;

                //장소 타입 저장해두기
                if (!nodeList.item(0).getNodeName().equals("contentid")) {
                    NodeList idNode = element.getElementsByTagName("contentid");
                    id = idNode.item(0).getChildNodes().item(0).getNodeValue();
                    placeID.add(Integer.parseInt(id));
                }

                //장소 타입 구별하기
                if (!nodeList.item(0).getNodeName().equals("contenttypeid")) {
                    NodeList typeNode = element.getElementsByTagName("contenttypeid");
                    int typeid = Integer.valueOf(typeNode.item(0).getChildNodes().item(0).getNodeValue());

                    switch (typeid) {
                        case 76:
                            type = "Tourism";
                            break;

                        case 78:
                            type = "Culture";
                            break;

                        case 85:
                            type = "Etc";
                            break;

                        case 75:
                            type = "Leisure";
                            break;

                        case 80:
                            type = "Lodging";
                            break;

                        case 79:
                            type = "Shopping";
                            break;

                        case 82:
                            type = "Food";
                            break;

                        case 77:
                            type = "Transportation";
                            break;
                    }
                }

                if (!nodeList.item(0).getNodeName().equals("title")) {
                    NodeList titleNode = element.getElementsByTagName("title");
                    title = titleNode.item(0).getChildNodes().item(0).getNodeValue();
                }

                if (!nodeList.item(0).getNodeName().equals("sigungucode")) {
                    NodeList sigunguNode = element.getElementsByTagName("sigungucode");
                    if (sigunguNode.item(0) != null) {
                        int guCode = Integer.parseInt(sigunguNode.item(0).getChildNodes().item(0).getNodeValue());
                        switch (guCode) {
                            case 1:
                                sigungu = "Gangnam-gu, Seoul";
                                break;
                            case 2:
                                sigungu = "Gangdong-gu, Seoul";
                                break;
                            case 3:
                                sigungu = "Gangbuk-gu, Seoul";
                                break;
                            case 4:
                                sigungu = "Gangseo-gu, Seoul";
                                break;
                            case 5:
                                sigungu = "Gwanak-gu, Seoul";
                                break;
                            case 6:
                                sigungu = "Gwangjin-gu, Seoul";
                                break;
                            case 7:
                                sigungu = "Guro-gu, Seoul";
                                break;
                            case 8:
                                sigungu = "Geumcheon-gu, Seoul";
                                break;
                            case 9:
                                sigungu = "Nowon-gu, Seoul";
                                break;
                            case 10:
                                sigungu = "Dobong-gu, Seoul";
                                break;
                            case 11:
                                sigungu = "Dongdaemun-gu, Seoul";
                                break;
                            case 12:
                                sigungu = "Dongjak-gu, Seoul";
                                break;
                            case 13:
                                sigungu = "Mapo-gu, Seoul";
                                break;
                            case 14:
                                sigungu = "Seodaemun-gu, Seoul";
                                break;
                            case 15:
                                sigungu = "Seocho-gu, Seoul";
                                break;
                            case 16:
                                sigungu = "Seongdong-gu, Seoul";
                                break;
                            case 17:
                                sigungu = "Seongbuk-gu, Seoul";
                                break;
                            case 18:
                                sigungu = "Songpa-gu, Seoul";
                                break;
                            case 19:
                                sigungu = "Yangcheon-gu, Seoul";
                                break;
                            case 20:
                                sigungu = "Yeongdeungpo-gu, Seoul";
                                break;
                            case 21:
                                sigungu = "Yongsan-gu, Seoul";
                                break;
                            default:
                                sigungu = "Seoul";
                                break;
                        }
                    }

                }

                if (!nodeList.item(0).getNodeName().equals("mapx")) {
                    NodeList xNode = element.getElementsByTagName("mapx");
                    if (xNode.item(0) != null) {
                        mapx = xNode.item(0).getChildNodes().item(0).getNodeValue();
                    }
                }

                if (!nodeList.item(0).getNodeName().equals("mapy")) {
                    NodeList yNode = element.getElementsByTagName("mapy");
                    if (yNode.item(0) != null) {
                        mapy = yNode.item(0).getChildNodes().item(0).getNodeValue();
                    }
                }

                addItem(title, type, sigungu, Double.parseDouble(mapx), Double.parseDouble(mapy));
            }
            addPlaceRecyclerView.setAdapter(addPlaceAdapter);
        }
    }
}



