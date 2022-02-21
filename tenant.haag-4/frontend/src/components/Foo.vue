<script setup lang="ts">
defineProps<{ msg: string }>()
</script>

<template>
  <h1>Foo: {{ msg }}</h1>
</template>

<style scoped>
h1 {
  background: lightblue;
}
</style>
