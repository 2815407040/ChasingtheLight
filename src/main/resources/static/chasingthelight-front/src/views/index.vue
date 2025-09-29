<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <header>
      <div class="container">
        <nav class="navbar">
          <a href="#" class="logo">摄影<span>器材</span></a>

          <ul class="nav-links">
            <li><a href="#" class="active" @click.prevent="$router.push('/index')">首页</a></li>
            <li><a href="#" @click.prevent="$router.push('/searchCamera')">相机</a></li>
            <li><a href="#">镜头</a></li>
            <li><a href="#">配件</a></li>
            <li><a href="#">评测</a></li>
            <li><a href="#">关于我们</a></li>
          </ul>

          <div class="search-bar">
            <input type="text" v-model="searchQuery" @keyup.enter="handleSearch" placeholder="搜索相机、品牌或型号...">
            <button @click="handleSearch"><i class="fa fa-search"></i></button>
          </div>

          <!-- 根据登录状态显示不同按钮 -->
          <div class="user-actions" v-if="isLoggedIn">
            <button class="user-btn with-text" @click="$router.push('/user/profile')" aria-label="用户中心">
              <i class="fa fa-user-circle"></i>
              <span class="btn-text">个人中心</span>
            </button>
            <button class="user-btn logout-btn with-text" @click="handleLogout" aria-label="退出登录">
              <i class="fa fa-sign-out"></i>
              <span class="btn-text">退出登录</span>
            </button>
          </div>
          <div class="user-actions" v-else>
            <button class="user-btn with-text" @click="$router.push('/login')" aria-label="登录">
              <i class="fa fa-user"></i>
              <span class="btn-text">登录</span>
            </button>
          </div>


        </nav>
      </div>
    </header>

    <el-main>
      <router-view></router-view>
    </el-main>

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

    <!-- 品牌展示 -->
    <section class="brands">
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

    <!-- 页脚 -->
    <footer>
      <div class="container">
        <div class="footer-content">
          <div class="footer-column">
            <h3>摄影器材</h3>
            <p>专业的相机推荐平台，为摄影爱好者提供最全面的器材信息和购买建议。</p>
            <div class="social-links">
              <a href="#"><i class="fa fa-facebook"></i></a>
              <a href="#"><i class="fa fa-twitter"></i></a>
              <a href="#"><i class="fa fa-instagram"></i></a>
              <a href="#"><i class="fa fa-youtube"></i></a>
            </div>
          </div>

          <div class="footer-column">
            <h3>快速链接</h3>
            <ul class="footer-links">
              <li><a href="#" @click.prevent="$router.push('/')">首页</a></li>
              <li><a href="#">相机</a></li>
              <li><a href="#">镜头</a></li>
              <li><a href="#">配件</a></li>
              <li><a href="#">评测</a></li>
            </ul>
          </div>

          <div class="footer-column">
            <h3>帮助中心</h3>
            <ul class="footer-links">
              <li><a href="#">购物指南</a></li>
              <li><a href="#">支付方式</a></li>
              <li><a href="#">配送信息</a></li>
              <li><a href="#">退换政策</a></li>
              <li><a href="#">常见问题</a></li>
            </ul>
          </div>

          <div class="footer-column">
            <h3>订阅资讯</h3>
            <p>订阅我们的邮件，获取最新的产品信息和摄影技巧。</p>
            <form class="newsletter-form">
              <input type="email" placeholder="你的邮箱地址">
              <button type="submit">订阅</button>
            </form>
          </div>
        </div>

        <div class="footer-bottom">
          <p>&copy; 2023 摄影器材网. 保留所有权利.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'Layout',
  data() {
    return {
      categories: [
        { id: 1, name: '全画幅微单', icon: 'fa-camera', count: 32 },
        { id: 2, name: 'APS-C画幅', icon: 'fa-camera-retro', count: 45 },
        { id: 3, name: '卡片相机', icon: 'fa-compact-disc', count: 28 },
        { id: 4, name: '单反相机', icon: 'fa-video-camera', count: 22 },
        { id: 5, name: '运动相机', icon: 'fa-bicycle', count: 18 },
        { id: 6, name: '中画幅相机', icon: 'fa-picture-o', count: 12 }
      ],
      searchQuery: ''
    };
  },
  computed: {
    // 计算属性判断登录状态
    isLoggedIn() {
      const token = localStorage.getItem('token');
      return !!token; // 存在token则认为已登录
    }
  },
  methods: {
    handleLogout() {
      // 清除本地存储的token
      localStorage.removeItem('token');
      // 跳转到登录页
      this.$router.push('/login');
      // 显示退出登录提示
      this.$message.success('已成功退出登录'); // 改用element的消息提示
    },
    handleSearch() {
      if (this.searchQuery.trim()) {
        // 跳转到搜索结果页并携带参数
        this.$router.push({
          path: '/searchCamera',
          query: { keyword: this.searchQuery.trim() }
        });
      }
    }
  }
}
</script>

<style scoped>
@import "../styles/common.css";
</style>