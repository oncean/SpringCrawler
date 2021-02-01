export default {
  dev: {
    '/api/': {
      target: 'https://preview.pro.ant.design',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
    '/carweler/': {
      target: 'http://localhost:7001',
      changeOrigin: true,
      pathRewrite: { '^/': '/' },
    },
    '/carwelerJava/': {
      target: 'http://localhost:8081',
      changeOrigin: true,
      pathRewrite: { '^/carwelerJava': '/' },
    },
  },
}