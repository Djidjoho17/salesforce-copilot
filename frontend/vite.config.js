import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    proxy: {
      // This forwards any request starting with /api
      // from React (port 5173) to Spring Boot (port 8080)
      // This is how the two apps talk to each other
      '/api': 'http://localhost:8080'
    }
  }
})