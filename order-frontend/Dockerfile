# ==========================================
# STAGE 1: Build the React Application
# ==========================================
FROM node:20-alpine AS builder

WORKDIR /app

# Copy the recipe card and install dependencies first
COPY package.json package-lock.json ./
RUN npm install

# Copy the rest of the source code and build the production bundle
COPY . .
RUN npm run build

# ==========================================
# STAGE 2: Serve with Nginx
# ==========================================
FROM nginx:alpine

# Copy the compiled static files from Stage 1 into the Nginx server directory
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose the standard web port
EXPOSE 80

# Start the Nginx server
CMD ["nginx", "-g", "daemon off;"]