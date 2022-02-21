<script setup lang="ts">
defineProps<{ msg: string }>()
</script>

<template>
  <h1>Bar: {{ msg }}</h1>
</template>

<style scoped>
h1 {
  background: lightgreen;
}
</style>
