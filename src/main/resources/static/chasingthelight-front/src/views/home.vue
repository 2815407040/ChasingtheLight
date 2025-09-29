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
              <div class="camera-rating">
                <i class="fa fa-star" v-for="n in 5" :key="n" :class="{ 'fa-star-o': n > camera.rating }"></i>
                <span>({{ camera.reviews }})</span>
              </div>
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
/* 英雄区域样式 */
.hero {
  background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('https://picsum.photos/id/1033/1600/600') center/cover no-repeat;
  color: white;
  padding: 100px 0;
  text-align: center;
}

.hero h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

.hero p {
  font-size: 18px;
  max-width: 700px;
  margin: 0 auto 30px;
}

.btn {
  display: inline-block;
  background-color: #3498db;
  color: white;
  padding: 12px 30px;
  border-radius: 5px;
  font-weight: 500;
  transition: background-color 0.3s, transform 0.3s;
  text-decoration: none;
}

.btn:hover {
  background-color: #2980b9;
  transform: translateY(-3px);
}

.btn-secondary {
  background-color: transparent;
  border: 2px solid white;
  margin-left: 15px;
}

.btn-secondary:hover {
  background-color: rgba(255,255,255,0.2);
}

/* 热门推荐样式 */
.section {
  padding: 80px 0;
}

.section-title {
  text-align: center;
  margin-bottom: 50px;
}

.section-title h2 {
  font-size: 32px;
  margin-bottom: 10px;
  color: #2c3e50;
}

.section-title p {
  color: #777;
  max-width: 600px;
  margin: 0 auto;
}

.cameras-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 30px;
}

.camera-card {
  background-color: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 5px 15px rgba(0,0,0,0.05);
  transition: transform 0.3s, box-shadow 0.3s;
}

.camera-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 30px rgba(0,0,0,0.1);
}

.camera-img {
  height: 200px;
  overflow: hidden;
}

.camera-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s;
}

.camera-card:hover .camera-img img {
  transform: scale(1.05);
}

.camera-info {
  padding: 20px;
}

.camera-brand {
  font-size: 14px;
  color: #777;
  margin-bottom: 5px;
}

.camera-name {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 10px;
}

.camera-rating {
  color: #f39c12;
  margin-bottom: 15px;
}

.camera-price {
  font-size: 20px;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 15px;
}

.camera-features {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 20px;
}

.feature-tag {
  font-size: 12px;
  background-color: #f1f1f1;
  padding: 3px 10px;
  border-radius: 20px;
}

.view-details {
  width: 100%;
  background-color: #3498db;
  color: white;
  padding: 10px;
  border-radius: 5px;
  font-weight: 500;
  transition: background-color 0.3s;
}

.view-details:hover {
  background-color: #2980b9;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero h1 {
    font-size: 36px;
  }

  .btn {
    display: block;
    width: 80%;
    margin: 0 auto 15px;
  }

  .btn-secondary {
    margin-left: auto;
    margin-right: auto;
  }
}

@media (max-width: 576px) {
  .hero {
    padding: 60px 0;
  }

  .hero h1 {
    font-size: 28px;
  }

  .section {
    padding: 50px 0;
  }

  .section-title h2 {
    font-size: 24px;
  }
}
</style>