<template>
  <div class="camera-card">
    <div class="camera-img">
      <img :src="camera.imgUrl" :alt="camera.model || camera.name">
    </div>

    <!-- 仅在需要时显示收藏图标（如热门推荐页面） -->
    <i
        v-if="showLikeIcon"
        class="fa-regular fa-heart heart-icon"
        :class="{ 'active': camera.liked }"
        @click="$emit('toggle-like', camera)"
    ></i>

    <div class="camera-info">
      <div class="camera-brand">{{ camera.brand }}</div>
      <div class="camera-name">{{ camera.model || camera.name }}</div>

      <!-- 仅在需要时显示评分（如搜索结果页面） -->
      <div v-if="showRating" class="camera-rating">
        <i class="fa fa-star" v-for="n in 5" :key="n" :class="{ 'fa-star-o': n > camera.rating }"></i>
        <span>({{ camera.reviews }})</span>
      </div>

      <div class="camera-price">¥{{ formatPrice(camera.price) }}</div>

      <div class="camera-features">
        <span class="feature-tag" v-for="feature in camera.features" :key="feature">{{ feature }}</span>
      </div>

      <button class="view-details" @click="$emit('view-details', camera)">查看详情</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CameraCard',
  props: {
    // 相机数据对象
    camera: {
      type: Object,
      required: true,
      default: () => ({})
    },
    // 是否显示收藏图标
    showLikeIcon: {
      type: Boolean,
      default: false
    },
    // 是否显示评分
    showRating: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    // 价格格式化
    formatPrice(price) {
      if (typeof price === 'number') {
        return price.toLocaleString();
      }
      return price;
    }
  }
};
</script>

<style scoped>
@import "../styles/common.css";
</style>