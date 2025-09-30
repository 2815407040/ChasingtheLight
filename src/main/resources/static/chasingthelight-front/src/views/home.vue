<template>
  <div class="app-container">
    <!-- 英雄区域 -->
    <section class="hero">
      <div class="container">
        <h1>发现适合你的完美相机</h1>
        <p>无论你是专业摄影师还是摄影爱好者，我们都能为你推荐最适合的摄影器材，助你捕捉生活中的每一个精彩瞬间。</p>
        <router-link to="/popularCameras" class="btn">热门相机</router-link>
        <a href="#" class="btn btn-secondary">查看最新评测</a>
      </div>
    </section>

    <!-- 热门推荐 -->
    <section class="section">
      <div class="container">
        <div class="section-title">
          <h2>热门推荐</h2>
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
  </div>
</template>

<script>
import CameraCard from "../components/CameraCard.vue";
import request from "../utils/request.js";

export default {
  name: 'HomePage',
  components: {
    CameraCard // 注册组件，这样模板中才能使用 <camera-card> 标签
  },
  data() {
    return {
      // 初始化时为空数组，等待后端数据
      featuredCameras: [],
    };
  },
  methods: {
    viewDetails(camera) {
      // 这里可以添加查看详情的逻辑，比如跳转到详情页
      console.log('查看相机详情:', camera);

    },
    toggleLike(camera) {
      camera.liked = !camera.liked;
    },
    // 获取热门相机数据
    async fetchPopularCameras() {
      try {
        const response = await request.get('/camera/popular')
        console.log('后端返回完整数据:', response);

        // 正确的层级解析：response.data 是接口返回的外层对象，包含 code、message、data
        const apiData = response.data;

        // 验证数据格式
        if (apiData.code === 200 && Array.isArray(apiData.data)) {
          // 先对原始数据进行切片，只取前6条
          const limitedData = apiData.data.slice(0, 6);

          this.featuredCameras = limitedData.map(camera => {
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
              features: features,
              liked: false
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
    this.fetchPopularCameras();
  }
};

</script>

<style scoped>
@import "../styles/common.css";
</style>