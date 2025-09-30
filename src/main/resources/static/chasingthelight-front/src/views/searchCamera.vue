<template>
  <div class="app-container">

    <section class="section">
      <div class="container">
        <div class="section-title">
          <h2>以下是搜索结果</h2>
          <p>精选高品质相机，满足不同摄影需求和预算，帮助你找到最适合的设备</p>
        </div>

        <div class="cameras-grid">
          <camera-card
              v-for="camera in featuredCameras"
              :key="camera.cameraId"
              :camera="camera"
              :show-like-icon="true"
              @view-details="viewDetails"
              @toggle-like="toggleLike"
          ></camera-card>
        </div>
      </div>
    </section>


    <!--    隐藏-->
    <section class="brands" style="visibility: hidden; height: 0; padding: 0; margin: 0;">
      <div class="container">
        <div class="section-title">
          <h2>合作品牌</h2>
          <p>我们只提供来自知名品牌的正品相机和摄影器材</p>
        </div>

        <div class="brands-container">
          <img src="https://picsum.photos/id/119/150/60" alt="佳能" class="brand-logo">
          <img src="https://picsum.photos/id/129/150/60" alt="尼康" class="brand-logo">
          <img src="https://picsum.photos/id/139/150/60" alt="索尼" class="brand-logo">
          <img src="https://picsum.photos/id/149/150/60" alt="富士" class="brand-logo">
          <img src="https://picsum.photos/id/159/150/60" alt="松下" class="brand-logo">
          <img src="https://picsum.photos/id/169/150/60" alt="徕卡" class="brand-logo">
        </div>
      </div>
    </section>

  </div>
</template>

<script>
import request from "../utils/request.js";
import CameraCard from "../components/CameraCard.vue";

export default {
  name: 'IndexPage',
  components: {CameraCard},
  data() {
    return {
      // 初始化时为空数组，等待后端数据
      featuredCameras: [],
    };
  },
  methods: {
    async fetchSearchCameras() {
      try {
        // 获取URL中的搜索参数
        const searchKeyword = this.$route.query.keyword || '';

        // 构造搜索参数对象
        const searchParams = {
          keyword: searchKeyword
          // 可以根据需要添加更多搜索条件
        };

        // 将参数传递给接口
        const response = await request.post('/camera/search', {
          keyword: searchKeyword  // 传递对象而非字符串
        });
        console.log('后端返回完整数据:', response);

        // 正确的层级解析：response.data 是接口返回的外层对象，包含 code、message、data
        const apiData = response.data;

        // 验证数据格式
        if (apiData.code === 200 && Array.isArray(apiData.data)) {
          this.featuredCameras = apiData.data.map(camera => {
            // 收集特性（过滤 null 值）
            const features = [];
            if (camera.design_style) features.push(camera.design_style);
            if (camera.pixel) features.push(camera.pixel);
            if (camera.stabilization) features.push(camera.stabilization);
            if (camera.videoRes) features.push(camera.videoRes);

            // 处理默认图片
            const defaultImg = `https://picsum.photos/seed/${camera.cameraId}/300/200`;

            return {
              cameraId: camera.cameraId,
              brand: camera.brand,
              model: camera.model,
              price: camera.price,
              imgUrl: camera.imgUrl || defaultImg,
              // 生成模拟评分和评论数
              rating: Math.floor(Math.random() * 2) + 4, // 4-5星
              reviews: Math.floor(Math.random() * 200) + 50, // 50-250条评论
              features: features
            };
          });
        } else {
          console.error('后端返回数据格式不正确');
        }
      } catch (error) {
        console.error('获取热门相机数据失败:', error);
      }
    }
  },
  mounted() {
    this.fetchSearchCameras();
  }
};
</script>

<style scoped>
@import "../styles/common.css";
</style>
