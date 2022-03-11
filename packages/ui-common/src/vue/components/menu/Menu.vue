<script setup lang="ts">
defineProps<{
  config: {
    groups: [
      {
        name: string,
        products: [
          {
            name: string,
            price: number,
          }
        ]
      }
    ]
  }
}>()
</script>

<template>
  <div class="menu-component">
    <div class="menu__group" v-for="group in config.groups">
      <h1 class="menu__title">{{ group.name }}</h1>
      <div class="menu__product" v-for="product in group.products">
        <div class="menu__name">{{ product.name }}</div>
        <div class="menu__price">{{ product.price }}</div>
      </div>
    </div>
  </div>
</template>
