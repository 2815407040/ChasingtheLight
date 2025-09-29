<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <header>
      <div class="container">
        <nav class="navbar">
          <a href="#" class="logo">摄影<span>器材</span></a>

          <ul class="nav-links">
            <li><a href="#" class="active">首页</a></li>
            <li><a href="#">相机</a></li>
            <li><a href="#">镜头</a></li>
            <li><a href="#">配件</a></li>
            <li><a href="#">评测</a></li>
            <li><a href="#">关于我们</a></li>
          </ul>

          <div class="search-bar">
            <input type="text" placeholder="搜索相机、品牌或型号...">
            <button><i class="fa fa-search"></i></button>
          </div>

          <div class="user-actions">
            <button @click="$router.push('/login')"><i class="fa fa-user"></i></button>
            <button class="logout-btn" @click="handleLogout">
              <i class="fa fa-sign-out"></i>
            </button>
          </div>
        </nav>
      </div>
    </header>

    <!-- 英雄区域 -->


    <!-- 热门推荐 -->
    <section class="section">
      <div class="container">
        <div class="section-title">
          <h2>热门推荐</h2>
          <p>精选高品质相机，满足不同摄影需求和预算，帮助你找到最适合的设备</p>
        </div>

        <div class="cameras-grid">
          <div class="camera-card" v-for="camera in featuredCameras" :key="camera.cameraId">
            <div class="camera-img">
              <img :src="camera.imgUrl" :alt="camera.model">
            </div>
            <div class="camera-info">
              <div class="camera-brand">{{ camera.brand }}</div>
              <div class="camera-name">{{ camera.model }}</div>
              <div class="camera-rating">
                <i class="fa fa-star" v-for="n in 5" :key="n" :class="{ 'fa-star-o': n > camera.rating }"></i>
                <span>({{ camera.reviews }})</span>
              </div>
              <div class="camera-price">¥{{ camera.price }}</div>
              <div class="camera-features">
                <span class="feature-tag" v-for="feature in camera.features" :key="feature">{{ feature }}</span>
              </div>
              <button class="view-details" @click="viewDetails(camera)">查看详情</button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 分类浏览 -->
    <section class="section categories">
      <div class="container">
        <div class="section-title">
          <h2>按类型浏览</h2>
          <p>根据你的摄影需求，选择合适类型的相机</p>
        </div>

        <div class="categories-grid">
          <div class="category-card" v-for="category in categories" :key="category.id">
            <div class="category-icon">
              <i :class="category.icon"></i>
            </div>
            <div class="category-name">{{ category.name }}</div>
            <div class="category-count">{{ category.count }} 款产品</div>
          </div>
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

export default {
  name: 'IndexPage',
  data() {
    return {
      // 初始化时为空数组，等待后端数据
      featuredCameras: [],
      categories: [
        { id: 1, name: '全画幅微单', icon: 'fa-camera', count: 32 },
        { id: 2, name: 'APS-C画幅', icon: 'fa-camera-retro', count: 45 },
        { id: 3, name: '卡片相机', icon: 'fa-compact-disc', count: 28 },
        { id: 4, name: '单反相机', icon: 'fa-video-camera', count: 22 },
        { id: 5, name: '运动相机', icon: 'fa-bicycle', count: 18 },
        { id: 6, name: '中画幅相机', icon: 'fa-picture-o', count: 12 }
      ]
    };
  },
  methods: {
    viewDetails(camera) {
      // 跳转到相机详情页，可以根据实际路由进行修改
      this.$router.push(`/camera-details/${camera.id}`);
      // 如果暂时没有详情页，可以使用弹窗显示信息
      // alert(`查看 ${camera.brand} ${camera.name} 的详情`);
    },
    handleLogout() {
      // 清除本地存储的token
      localStorage.removeItem('token');
      // 跳转到登录页
      this.$router.push('/login');
      // 显示退出登录提示
      alert('已成功退出登录');
    },
    // 获取热门相机数据
    // 修改 fetchPopularCameras 方法
    async fetchPopularCameras() {
      try {
        const response = await request.get('/camera/popular')
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
    this.fetchPopularCameras();
  }
};
</script>

<style scoped>
/* 基础样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

body {
  line-height: 1.6;
  color: #333;
  background-color: #f9f9f9;
}

.container {
  width: 90%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

a {
  text-decoration: none;
  color: inherit;
}

ul {
  list-style: none;
}

button {
  cursor: pointer;
  border: none;
  background: none;
}

img {
  max-width: 100%;
  height: auto;
}

/* 导航栏 */
header {
  background-color: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
}

.logo {
  font-size: 24px;
  font-weight: bold;
  color: #2c3e50;
}

.logo span {
  color: #3498db;
}

.nav-links {
  display: flex;
  gap: 30px;
}

.nav-links a {
  font-weight: 500;
  transition: color 0.3s;
}

.nav-links a:hover {
  color: #3498db;
}

.search-bar {
  display: flex;
  align-items: center;
  background-color: #f1f1f1;
  border-radius: 20px;
  padding: 5px 15px;
  width: 300px;
}

.search-bar input {
  border: none;
  background: transparent;
  padding: 8px;
  width: 100%;
  outline: none;
}

.search-bar button {
  color: #777;
}

.user-actions {
  display: flex;
  gap: 20px;
  align-items: center;
}

.user-actions button {
  font-size: 18px;
  color: #333;
  transition: color 0.3s;
}

.user-actions button:hover {
  color: #e74c3c; /* 退出登录按钮 hover 时变红 */
}

/* 英雄区域 */
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

/* 热门推荐 */
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

/* 分类浏览 */
.categories {
  background-color: white;
}

.categories-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 30px;
}

.category-card {
  text-align: center;
  padding: 30px;
  border-radius: 10px;
  background-color: #f9f9f9;
  transition: transform 0.3s, box-shadow 0.3s;
}

.category-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 10px 20px rgba(0,0,0,0.1);
}

.category-icon {
  font-size: 40px;
  color: #3498db;
  margin-bottom: 20px;
}

.category-name {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 10px;
}

.category-count {
  color: #777;
  font-size: 14px;
}

/* 品牌展示 */
.brands {
  background-color: #f1f1f1;
  padding: 60px 0;
}

.brands-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
  gap: 40px;
}

.brand-logo {
  height: 60px;
  opacity: 0.7;
  transition: opacity 0.3s, transform 0.3s;
}

.brand-logo:hover {
  opacity: 1;
  transform: scale(1.1);
}

/* 页脚 */
footer {
  background-color: #2c3e50;
  color: white;
  padding: 60px 0 20px;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 40px;
  margin-bottom: 40px;
}

.footer-column h3 {
  font-size: 18px;
  margin-bottom: 20px;
  position: relative;
  padding-bottom: 10px;
}

.footer-column h3::after {
  content: '';
  position: absolute;
  left: 0;
  bottom: 0;
  width: 40px;
  height: 2px;
  background-color: #3498db;
}

.footer-links li {
  margin-bottom: 10px;
}

.footer-links a {
  color: #bdc3c7;
  transition: color 0.3s;
}

.footer-links a:hover {
  color: white;
  padding-left: 5px;
}

.social-links {
  display: flex;
  gap: 15px;
  margin-top: 20px;
}

.social-links a {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: rgba(255,255,255,0.1);
  transition: background-color 0.3s, transform 0.3s;
}

.social-links a:hover {
  background-color: #3498db;
  transform: translateY(-5px);
}

.newsletter-form {
  margin-top: 20px;
}

.newsletter-form input {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: none;
  margin-bottom: 10px;
}

.newsletter-form button {
  background-color: #3498db;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.newsletter-form button:hover {
  background-color: #2980b9;
}

.footer-bottom {
  text-align: center;
  padding-top: 20px;
  border-top: 1px solid rgba(255,255,255,0.1);
  color: #bdc3c7;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 992px) {
  .search-bar {
    width: 200px;
  }
}

@media (max-width: 768px) {
  .navbar {
    flex-wrap: wrap;
  }

  .search-bar {
    order: 3;
    width: 100%;
    margin-top: 15px;
  }

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
  .nav-links {
    gap: 15px;
  }

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
