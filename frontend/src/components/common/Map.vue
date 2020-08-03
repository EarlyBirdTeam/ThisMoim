<template>
  <div>
      <button @click="kaka">kakao</button>
      <button @click="mulcam">멀캠</button>
     
    <div id="map"></div>
  </div>
</template>

<script>
export default {
    data() {
    return {
        N:0,
        E:0
    };
    }
    ,
    mounted() {
        if (window.kakao && window.kakao.maps) {
            this.initMap();
        } else {
            const script = document.createElement('script');
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap);
            script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=506c56730f934be446be361e5bbd02f4&libraries=services';
            document.head.appendChild(script);
        }
    },
    methods: {
        initMap() {
            var container = document.getElementById('map');

            var options = {
              center: new kakao.maps.LatLng(this.N, this.E),
              level: 3
            };

            var map = new kakao.maps.Map(container, options);
            map.setMapTypeId(kakao.maps.MapTypeId.HYBRID);
        },
        kaka() {
            this.N=33.452701;
            this.E=126.570667;
            this.initMap()
  
        },
        mulcam() {
            this.N=37.501715
            this.E=127.039703
            this.initMap()
        }
    }
}
</script>

<style>
#map {
    width: 400px;
    height: 300px;
}
</style>