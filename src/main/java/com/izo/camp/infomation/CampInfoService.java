package com.izo.camp.infomation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.izo.camp.mapper.CampInfoMapper;
import com.izo.camp.vo.CampInfoVO;

@Service
public class CampInfoService {

	@Autowired
	CampInfoMapper campInfoMapper;
	
	//테이블에 추가 
	public void hasCamping() {
		if(campInfoMapper.getTotalNum() == 0) {
			System.out.println("---------------");
			System.out.println("CampInfo 테이블이 없습니다 Load 합니다");
			insertInfo();
		}else {
		}
	}
	
	/*
		22.08.27 JSONParser 와 Rest API 사용에 대해 .
		RestAPI를 사용해서 받아오는 ResponseBody를 HTTP컨버터가 작동하게 해서 사용하면 편리할것. 
		나의 애플리케이션에서 다른 오픈 RestAPI를 요청하는 방법에 대해 찾아 봐야 한다.
	*/
	//테이블이 비어있다면 이 테이블을 채워 넣어준다. 
	//JSON -> 테이블 만들기
	public void insertInfo() {
		//테이블이 비어있다면 실행X
		JSONParser jsonParser = new JSONParser();
		
		if(campInfoMapper.getTotalNum() != 0) {
			
			return;
		}
		else {
//			
			ClassPathResource resource = new ClassPathResource("/datafile/campArea.json");
			// 1. 파일을 parse해서 obj 형태로 받는다.
			try {
				Object obj = jsonParser.parse(new FileReader(resource.getFile()));
			// 2. jsonobject 로 형변환 해준다.
				JSONObject jsonObject = (JSONObject)obj;
			// 3. jsonObject에서 원하는 정보를 찾아온다.
			// 	  String fieldName = (String)jsonObject.get("key값");
			// 4. Array 형식면 JSONArray형식을 바꿔준다.
				JSONArray fieidsList = (JSONArray)jsonObject.get("fields");
			// 5. Array 속에서 원하는 정보를 가져온다.
				JSONObject jsonObject2 = (JSONObject)fieidsList.get(0);
				String name = (String)jsonObject2.get("id");
				//JSONObject campInfoArr = (JSONObject)campList.get(1);
			
			//실제 사용부 
				Set<String> nameSet = new HashSet<String>();
				JSONArray campList = (JSONArray)jsonObject.get("records");
				int idx = 0;
				
				for(Object campObj : campList) {
					JSONObject camp = (JSONObject)campObj;
					
					//insert 할 CampInfoVO 생성
					if(nameSet.contains((String)camp.get("야영(캠핑)장명"))) {
						continue;
					}else {
						nameSet.add((String)camp.get("야영(캠핑)장명"));
					}
					CampInfoVO campInfoVO = new CampInfoVO();
					//이름
					campInfoVO.setName((String)camp.get("야영(캠핑)장명"));
					
					//위도 ,경도
					campInfoVO.setLatitude(Double.parseDouble((String)camp.get("위도")));
					campInfoVO.setLongitude(Double.parseDouble((String)camp.get("경도")));
					
					//주소 전화번호
					String address = (String)camp.get("소재지도로명주소");
					if(address==null) {
						address=(String)camp.get("소재지지번주소");
					}
					campInfoVO.setAddress(address);
					campInfoVO.setTell((String)camp.get("야영장전화번호"));
			
					//이미지 파일 이름
					idx++;
					String imgName = String.format("campingArea%02d.jpg", idx%30 + 1 );
					campInfoVO.setImgName(imgName);
					campInfoMapper.addNewCamp(campInfoVO);
				}
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	//나의 위치 받아오기 
	public Map<String,Double> getKakaoApiFromAddress(String roadFullAddr) {
	    String apiKey = "1c7ac58832774fc482b8cc40b799e103";
	    String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
	    Map<String, Double> resultMap = new HashMap<String, Double>();
	    
	    /*
	     * MemberMapper를 통해서 받던지 지금 로그인된 상태의 지역에서 받아서 사용 
	     * 로그인이 되지 않은 상태에서는 어떻게 할까? 
	     * 지도를 입력해주세요 혹은 로그인후  이용해주세요 ? 지역을 설정해주세요 ? 
	     */
	    
	    try {
	        roadFullAddr = roadFullAddr.substring(0,roadFullAddr.indexOf(","));
	        roadFullAddr = URLEncoder.encode(roadFullAddr, "UTF-8");
	        
	        String addr = apiUrl + "?query=" + roadFullAddr;

	        URL url = new URL(addr);
	        URLConnection conn = url.openConnection();
	        conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

	        BufferedReader rd = null;
	        rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        StringBuffer docJson = new StringBuffer();

	        String line;

	        while ((line=rd.readLine()) != null) {
	            docJson.append(line);
	        }
	        JSONParser parser = new JSONParser();
	        Object obj = parser.parse( docJson.toString() );
	        JSONObject jobj = (JSONObject)obj;
	        
	        JSONArray fieidsList = (JSONArray)jobj.get("documents");
	        JSONObject fieid = (JSONObject)fieidsList.get(0);
	        String address = (String)fieid.get("address_name");
	        
	        //나의 경도
	        String x = (String)fieid.get("x");
	        //나의 위도
	        String y = (String)fieid.get("y");
	        resultMap.put("lon", Double.parseDouble(x));
	        resultMap.put("lat", Double.parseDouble(y));
	        
	        rd.close();

	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return resultMap;
	}
	
	
	//가까운 캠핑장들을 받아오는것 - 좌표로
	public List<CampInfoVO> getNearCampingArea(Map<String,Double> coordination){
		//받아온 주소
		
		//Map<String,Double> coordination = getKakaoApiFromAddress(address);
		//나의 주소를 기반으로 가까운 캠핑장 얻기
		
		//범위 설정을 맵으로 넣어준다 .
		//위도
		coordination.put("limitLat",0.36);
		//경도
		coordination.put("limitLon",0.45);
		
		
		List<CampInfoVO> campList = campInfoMapper.nearCampArea(coordination);
		
		//내 주소 위도 경도 기반으로 주어진 campList의 거리값들을 계산해줌
		Double myLat = coordination.get("lat");
		Double myLon = coordination.get("lon");
		for(int i = 0 ; i < campList.size() ; i++) {
			Double distance = distanceInKilometerByHaversine(myLat, myLon, campList.get(i).getLatitude(), campList.get(i).getLongitude());
			//거리 셋팅
			distance = Math.round(distance * 100)/100.00;
			campList.get(i).setDistance(distance);
		}
		
		return campList;
	}
	
	
	//두 지점 사이의 km 구하기
	public static double distanceInKilometerByHaversine(double x1, double y1, double x2, double y2) {
	    double distance;
	    double radius = 6371; // 지구 반지름(km)
	    double toRadian = Math.PI / 180;

	    double deltaLatitude = Math.abs(x1 - x2) * toRadian;
	    double deltaLongitude = Math.abs(y1 - y2) * toRadian;

	    double sinDeltaLat = Math.sin(deltaLatitude / 2);
	    double sinDeltaLng = Math.sin(deltaLongitude / 2);
	    double squareRoot = Math.sqrt(
	        sinDeltaLat * sinDeltaLat +
	        Math.cos(x1 * toRadian) * Math.cos(x2 * toRadian) * sinDeltaLng * sinDeltaLng);

	    distance = 2 * radius * Math.asin(squareRoot);

	    return distance;
	}
	
	//인덱스로 캠핑장 정보 받아오기
	public CampInfoVO campInfoByIndex(Integer idx) {
		
		return campInfoMapper.getCampingByIdx(idx);
	}
}
