package com.example.travellet.feature.place;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.travellet.R;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PlaceActivity extends AppCompatActivity {
    //탭 리스트
    List<String> tabList = Arrays.asList("all", "lodging", "food", "shopping",
                                                    "tourism", "culture","leisure");

    //카테고리 관련 변수
    boolean lodgingState=false, foodState=false, shoppingState=false, tourismState=false, etcState=false, cultureState=false, leisureState = false, transportationState=false;
    boolean keywordState=false;
    String searchKeyword;
    int searchType;

    MenuItem placeSearch;

    //리사이클러에 필요한 변수;
    ArrayList<Integer> placeID = new ArrayList<Integer>();
    ArrayList<Double> placeX = new ArrayList<Double>();
    ArrayList<Double> placeY = new ArrayList<Double>();
    ArrayList<String> placeTitle = new ArrayList<String>();
    ArrayList<String> placeAddr = new ArrayList<String>();

    RecyclerView placeRecyclerView = null ;
    PlaceAdapter placeAdapter = null ;
    ArrayList<PlaceItem> placeItems = new ArrayList<PlaceItem>();
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        initToolbar();
        initTab();

        placeRecyclerView = findViewById(R.id.recyclerview_place);
        //리사이클러뷰에 adapter 객체 지정
        placeAdapter = new PlaceAdapter(placeItems);
        placeRecyclerView.setAdapter(placeAdapter);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2); // 리사이클러뷰 그리드 레이아웃
        placeRecyclerView.setLayoutManager(layoutManager);
    }

    //툴바 메뉴 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);

        placeSearch = menu.findItem(R.id.place_search); //돋보기 버튼 등록
        SearchView searchView = (SearchView)placeSearch.getActionView(); //MenuItem으로 SearchView 변수 생성
        searchView.setSubmitButtonEnabled(true); // 확인 버튼 활성화
        //searchView의 검색 이벤트
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //검색 버튼을 눌렀을 경우
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                //스트링 변수 keyword는 입력한 검색어
                keywordState = true;
                searchKeyword = keyword;
                if(lodgingState) {
                    searchType = 80;
                }
                else if(foodState) {
                    searchType = 82;
                }
                else if(shoppingState) {
                    searchType = 79;
                }
                else if(tourismState) {
                    searchType = 76;
                }
                else if(cultureState) {
                    searchType = 78;
                }
                else if(leisureState) {
                    searchType = 75;
                }
                else if(transportationState){
                    searchType = 77;
                }
                else if(etcState) {
                    searchType = 85;
                }
                else
                    searchType = -1;
                placeItems.clear();
                placeID.clear();
                getPlaceListData(keyword, searchType);
                return true;
            }

            //텍스트가 바뀔 때마다 호출
            @Override
            public boolean onQueryTextChange(String place) {
                return false;
            }
        });
        return true;
    }

    // 검색 확장, 축소를 버튼으로 생성


    //툴바 초기화 메소드
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_place);
        setSupportActionBar(toolbar);

        //추가적으로 searchView 구현 필요함.
    }

    //탭 초기화 메소드
    private void initTab() {
        TabLayout tabLayout = findViewById(R.id.tab_place);
        //탭 레이아웃에 탭 추가
        for(int i=0; i<tabList.size(); i++)
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));

        //탭 선택 이벤트 처리리
       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition() ;
                selectCategory(pos) ;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO : tab의 상태가 선택되지 않음으로 변경.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO : 이미 선택된 tab이 다시
            }
        });
    }

    private void selectCategory(int position){
        switch (position) {
            case 0:
                lodgingState = false;
                foodState = false;
                shoppingState = false;
                tourismState = false;
                etcState = false;
                cultureState = false;
                leisureState = false;
                transportationState = false;
                if(keywordState){
                    searchType = -1;
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
            case 1:
                lodgingState = true;
                foodState = false;
                shoppingState = false;
                tourismState = false;
                etcState = false;
                cultureState = false;
                leisureState = false;
                transportationState = false;
                if(keywordState){
                    searchType = 80;
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
            case 2:
                lodgingState = false;
                foodState = true;
                shoppingState = false;
                tourismState = false;
                etcState = false;
                cultureState = false;
                leisureState = false;
                transportationState = false;
                Log.d("type", String.valueOf(searchType));
                if(keywordState){
                    searchType = 82;
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
            case 3:
                lodgingState = false;
                foodState = false;
                shoppingState = true;
                tourismState = false;
                etcState = false;
                cultureState = false;
                leisureState = false;
                transportationState = false;
                if(keywordState){
                    searchType = 79;
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
            case 4:
                lodgingState = false;
                foodState = false;
                shoppingState = false;
                tourismState = true;
                etcState = false;
                cultureState = false;
                leisureState = false;
                transportationState = false;
                Log.d("type", String.valueOf(searchType));
                if(keywordState){
                    searchType = 76;
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
            case 5:
                lodgingState = false;
                foodState = false;
                shoppingState = false;
                tourismState = false;
                etcState = true;
                cultureState = false;
                leisureState = false;
                transportationState = false;
                if(keywordState){
                    searchType = 85;
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
            case 6:
                lodgingState = false;
                foodState = false;
                shoppingState = false;
                tourismState = false;
                etcState = false;
                cultureState = true;
                leisureState = false;
                transportationState = false;
                if(keywordState){
                    searchType = 78;
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
            case 7:
                lodgingState = false;
                foodState = false;
                shoppingState = false;
                tourismState = false;
                etcState = false;
                cultureState = false;
                leisureState = true;
                transportationState = false;
                if(keywordState){
                    searchType = 75;
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
            case 8:
                lodgingState = false;
                foodState = false;
                shoppingState = false;
                tourismState = false;
                etcState = false;
                cultureState = false;
                leisureState = false;
                transportationState = true;
                if(keywordState){
                    placeItems.clear();
                    placeID.clear();
                    getPlaceListData(searchKeyword, searchType);
                }
                break;
        }

    }

    //장소 어댑터 아이템 추가
    public void addItem(String thumb, String title, String addr) {
        PlaceItem item = new PlaceItem(thumb, title, addr);
        item.setPlaceListThumb(thumb);
        item.setPlaceListTitle(title);
        item.setPlaceListAddr(addr);
        placeItems.add(item);
    }


    //장소 검색 결과 리스트를 보여주는 메소드
    public void getPlaceListData(String keyword, int searchType) {
        try {
            String serviceKey = "x%2FB48ucBtE1tDbI%2FOOc%2B0Qh3MP%2BlYEETjSL5Q8G0L912refn%2FEii%2FgZ5E0S%2Bdqs%2BAmxagAo%2B9%2BieRWWN80QxNA%3D%3D";

            StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/EngService/searchKeyword"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8")); /*공공데이터포털에서 발급받은 인증키*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("AND", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("Travellet", "UTF-8")); /*서비스명=어플명*/
            urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*목록 구분(Y=목록, N=개수)*/
            urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("B", "UTF-8")); /*(A=제목순,B=조회순,C=수정일순,D=생성일순) 대표이미지가 반드시 있는 정렬(O=제목순, P=조회순, Q=수정일순, R=생성일순)*/
            if(searchType != -1){
                urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(searchType), "UTF-8")); /*관광타입(관광지, 숙박 등)ID*/
            }
            urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*지역코드*/
            urlBuilder.append("&" + URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")); /*검색 요청할 키워드(국문=인코딩 필요)*/
            URL url = new URL(urlBuilder.toString());

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
                url = new URL((String) urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Document doc) {
            String title = "", address = "", type = "", mapx="", mapy="";
            NodeList nodeList = doc.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                String thumbnail = " ";

                Node node = nodeList.item(i);
                Element element = (Element) node;

                if (!nodeList.item(0).getNodeName().equals("firstimage")) {
                    NodeList imageNode = element.getElementsByTagName("firstimage");
                    if(imageNode.item(0) !=null){
                        thumbnail = imageNode.item(0).getChildNodes().item(0).getNodeValue();
                    }
                }

                if (!nodeList.item(0).getNodeName().equals("contentid")) {
                    NodeList idNode = element.getElementsByTagName("contentid");

                    //placeID.add(Integer.parseInt(idNode.item(0).getChildNodes().item(0).getNodeValue()));
                }

                if (!nodeList.item(0).getNodeName().equals("title")) {
                    NodeList titleNode = element.getElementsByTagName("title");
                    title = titleNode.item(0).getChildNodes().item(0).getNodeValue();
                    placeTitle.add(titleNode.item(0).getChildNodes().item(0).getNodeValue());
                }
                if (!nodeList.item(0).getNodeName().equals("addr1")) {
                    NodeList addressNode = element.getElementsByTagName("addr1");
                    address = addressNode.item(0).getChildNodes().item(0).getNodeValue();
                    placeAddr.add(addressNode.item(0).getChildNodes().item(0).getNodeValue());
                }
                if (!nodeList.item(0).getNodeName().equals("contenttypeid")) {
                    NodeList typeNode = element.getElementsByTagName("contenttypeid");
                    int typeid = Integer.valueOf(typeNode.item(0).getChildNodes().item(0).getNodeValue());

                    switch (typeid) {
                        case 76:
                            type = "Tourism";
                            break;

                        case 78:
                            type = "culture";
                            break;

                        case 85:
                            type = "etc.";
                            break;

                        case 75:
                            type = "leisure/sports";
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
                            type = "transportation";
                            break;
                    }
                    if (!nodeList.item(0).getNodeName().equals("mapx")) {
                        NodeList xNode = element.getElementsByTagName("mapx");
                        if(xNode.item(0) !=null){
                            mapx = xNode.item(0).getChildNodes().item(0).getNodeValue();
                            placeX.add(Double.parseDouble(xNode.item(0).getChildNodes().item(0).getNodeValue()));
                            Log.d("placeX: ", mapx);
                        }
                    }

                    if (!nodeList.item(0).getNodeName().equals("mapy")) {
                        NodeList yNode = element.getElementsByTagName("mapy");
                        if(yNode.item(0) !=null){
                            mapy = yNode.item(0).getChildNodes().item(0).getNodeValue();
                            placeY.add(Double.parseDouble(yNode.item(0).getChildNodes().item(0).getNodeValue()));
                            Log.d("placeY: ", mapy);
                        }
                    }
                }

                //db에 좋아요 눌렀던 항목 저장, 리스트뷰에 추가할 때 db에 등록되어 있는 애들은 true로 넘길 수 있도록
                if(thumbnail.equals(" ")){
                    addItem("NULL", title, address);
                }
                else
                    addItem(thumbnail, title, address);
            }
            placeRecyclerView.setAdapter(placeAdapter);
        }

    }
}