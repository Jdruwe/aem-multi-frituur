<script setup lang="ts">
defineProps<{ config: { image: string, alt: string } }>()
</script>

<template>
  <div class="heading-component">
    <img :src="config.image" :alt="config.alt">
  </div>
</template>
