<script setup lang="ts">
defineProps<{ config: { message: string } }>()
</script>

<template>
  <div class="menu">
    <p>{{ config.message }}</p>
  </div>
</template>
