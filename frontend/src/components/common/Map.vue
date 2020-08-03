<template>
  <v-container class="page-map">
    <v-btn @click="kaka">kakao</v-btn>
    <v-btn @click="mulcam">멀캠</v-btn>
    <br />
    <v-row>
      <v-col cols="9">
        <v-text-field 
        v-model="search"
        @keyup.enter="searchEvent" 
        label="Place"></v-text-field>
      </v-col>
      <v-col cols="3">
        <v-btn @click="searchEvent">search</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <div id="map"></div>
      </v-col>
    </v-row>
    <v-btn @click="saveEvent">저장</v-btn>
    {{ this.coord }}
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      search: "카카오",
      map: Object,
      markers: [],
      coord: {
        place_name: "",
        x: null,
        y: null,
      }
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=506c56730f934be446be361e5bbd02f4&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      var container = document.getElementById("map");

      var options = {
        center: new kakao.maps.LatLng(0, 0),
        level: 3,
      };

      var map = new kakao.maps.Map(container, options);
      this.map = map;
      this.map.setMapTypeId(kakao.maps.MapTypeId.HYBRID);

      var moveLatLon = new kakao.maps.LatLng(33.452701, 126.570667);
      this.map.panTo(moveLatLon);
    },
    kaka() {
      var moveLatLon = new kakao.maps.LatLng(33.452701, 126.570667);
      this.map.panTo(moveLatLon);
    },
    mulcam() {
      var moveLatLon = new kakao.maps.LatLng(37.501715, 127.039703);
      this.map.panTo(moveLatLon);
    },
    searchEvent() {
      // 이미 찍힌 마커들을 보이지 않게 하고 마커배열을 초기화합니다
      var markers = this.markers;
      for (var i=0; i < markers.length; i++) {
        markers[i].setMap(null);
      };
      markers = [];

      // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places();
      var map = this.map;
      var coord = this.coord;
      // 키워드로 장소를 검색합니다
      ps.keywordSearch(this.search, placesSearchCB);

      // 키워드 검색 완료 시 호출되는 콜백함수 입니다
      function placesSearchCB(data, status) {
        if (status === kakao.maps.services.Status.OK) {
          // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
          // LatLngBounds 객체에 좌표를 추가합니다
          var bounds = new kakao.maps.LatLngBounds();

          for (var i = 0; i < data.length; i++) {
            displayMarker(data[i]);
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
          }
          for (var i = 0; i< markers.length; i++) {
            markers[i].setMap(map);
          }
          // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
          map.setBounds(bounds);
        }
      }

      // 지도에 마커를 표시하는 함수입니다
      function displayMarker(place) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(place.y, place.x),
        });

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "click", function () {
          // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
          coord.x = place.x;
          coord.y = place.y;
          infowindow.setContent(
            '<div style="padding:5px;font-size:12px;">' +
              place.place_name +
              "</div>"
          );
          infowindow.open(map, marker);
        });
        
        markers.push(marker);
      }
    },
    saveEvent() {
      // 서버에 coord 값 보내기
      
    }
  },
};
</script>

<style>
#map {
  height: 500px;
  margin-top: 20px;
}
/* .page-map button {
  border: solid 1px;
  border-radius: 5px;
  margin-left: 5px;
} */
/* .page-map input {
  border: solid 1px;
  border-radius: 5px;
  margin-left: 5px;
} */
</style>