# Use the official Nginx base image
FROM nginx:1.21.4

# Copy Nginx configuration file to the container
COPY nginx.conf /etc/nginx/nginx.conf

# Expose the ports
EXPOSE 80

# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]