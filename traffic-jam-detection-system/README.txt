Cách tạo GET/POST function trong spring

1- Trong controller interface, tạo 1 method có annotion get: @ @GetMapping(link...)
2- Implement method trong controllerImpl, gọi service bằng @Autowired. Lưu ý gọi interface Service không phải ServiceImpl
3- Sử dụng service và serviceImpl giống ở controller. mọi method phải được khai báo trong interface.
4- Lưu database thì ở service @Autowired đến repository mình cần. repository là interface sử dụng Spring JPA nên không cần viết query.

