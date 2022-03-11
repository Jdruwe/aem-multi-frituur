import {defineConfig} from 'vite';
import vue from '@vitejs/plugin-vue';
import postcssNesting from 'postcss-nesting';

export default defineConfig({
    server: {
        port: 3000,
        strictPort: true,
    },
    css: {
        postcss: {
            plugins: [
                postcssNesting()
            ]
        }
    },
    build: {
        outDir: 'dist/clientlib-base',
        brotliSize: false,
        manifest: true,
        rollupOptions: {
            output: {
                assetFileNames:
                    'etc.clientlibs/haag-4/clientlibs/clientlib-base/resources/[ext]/[name].[hash][extname]',
                chunkFileNames:
                    'etc.clientlibs/haag-4/clientlibs/clientlib-base/resources/chunks/[name].[hash].js',
                entryFileNames:
                    'etc.clientlibs/haag-4/clientlibs/clientlib-base/resources/js/[name].[hash].js',
            },
            input: {
                app: 'src/main.ts',
            },
        },
    },
    plugins: [vue()],
});
