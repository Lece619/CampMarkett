# CampMarket 프로젝트
 
시연 영상입니다.

https://drive.google.com/file/d/1MCkRVwfvdGbxB-klp0WNgBG2Z2H3_rzW/view?usp=sharing
-화질이 저하되므로 다운로드 후 재생해 주세요

<img width="575" alt="main" src="https://user-images.githubusercontent.com/25758530/177305229-4e4849a2-7d08-42ec-a7a9-01880cc8e23f.PNG">
<img width="536" alt="market" src="https://user-images.githubusercontent.com/25758530/177305240-80726962-775a-46bd-865f-2dc3b87465e8.PNG">


<img width="387" alt="관계도" src="https://user-images.githubusercontent.com/25758530/177303549-b438ab51-fc50-4ebc-b4fe-63f751af72f7.PNG">


## 보충되어야 할 점!

1. 주문완료 페이지에 redirection으로 재주문되는것을 막기 ( 현재에도 막혀있긴함 - DB를 조회하는 방식이기 때문 ) 
   현재 코드도 막혀있긴하지만 실제 주문이 안들어가는것이 아닌 빈 주문이 들어가는 형식 - redirect: 주문완료페이지?오더 번호 로 리다이렉션하기
   
2. VO 객체에 대한 빈 등록을 사용하지 않아도 괜찮다.

3. 모든 빈에 대하여 의존관계 주입이 필드 주입으로 이뤄져 있어서 단위 테스트 실행에 문제가 크고, 무슨 의존관계가 주입되는지 명확하게 파악하기 힘들다. 

4. /페이징 처리 -- > 우선 데이터가 한번 필터링 되어있기 때문에 걸러진 데이터를 전부 가져온 상태에서 페이징할 숫자를 가져온것. 
	// 쿼리를 조금더 효과적으로 사용하게 된다면 SQL쿼리를 날릴때 페이징될 순서로 가져오는 것이 좋을 수 있다. 지금은 데이터가 적기 때문에 가능한 일
