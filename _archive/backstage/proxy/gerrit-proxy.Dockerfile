FROM nginx

COPY ./gerrit-proxy.nginx.conf /etc/nginx/conf.d/01.conf