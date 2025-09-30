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
          <div class="camera-card" v-for="camera in featuredCameras" :key="camera.id">
            <div class="camera-img">
              <img :src="camera.image" :alt="camera.name">
            </div>
            <div class="camera-info">
              <div class="camera-brand">{{ camera.brand }}</div>
              <div class="camera-name">{{ camera.name }}</div>
              <div class="camera-price">¥{{ camera.price.toLocaleString() }}</div>
              <div class="camera-features">
                <span class="feature-tag" v-for="feature in camera.features" :key="feature">{{ feature }}</span>
              </div>
              <button class="view-details" @click="viewDetails(camera)">查看详情</button>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      featuredCameras: [
        {
          id: 1,
          brand: '索尼',
          name: 'Alpha 7 III 全画幅微单相机',
          image: 'https://picsum.photos/id/1015/500/300',
          rating: 4.8,
          reviews: 256,
          price: 12999,
          features: ['全画幅', '2420万像素', '5轴防抖', '4K视频']
        },
        {
          id: 2,
          brand: '佳能',
          name: 'EOS R5 专业全画幅微单',
          image: 'https://picsum.photos/id/1016/500/300',
          rating: 4.9,
          reviews: 189,
          price: 25999,
          features: ['4500万像素', '8K视频', '双像素对焦', '防水']
        },
        {
          id: 3,
          brand: '尼康',
          name: 'Z6 II 全画幅微单相机',
          image: 'https://picsum.photos/id/1018/500/300',
          rating: 4.7,
          reviews: 145,
          price: 13799,
          features: ['2450万像素', '4K视频', '双EXPEED 6', 'Wi-Fi']
        },
        {
          id: 4,
          brand: '富士',
          name: 'X-T4 无反相机',
          image: 'https://picsum.photos/id/1019/500/300',
          rating: 4.6,
          reviews: 203,
          price: 8799,
          features: ['2610万像素', '4K 60p', '五轴防抖', '复古设计']
        }
      ]
    };
  },
  methods: {
    viewDetails(camera) {
      // 跳转到相机详情页
      this.$router.push(`/camera-details/${camera.id}`);
    }
  }
};

</script>

<style scoped>
@import "../styles/common.css";
</style>