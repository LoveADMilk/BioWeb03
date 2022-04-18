Thymeleaf静态文件路径问题JS、CSS、img

配置文件：spring.resources.static-locations=classpath:/static/

CSS链接 要用HREF

```html
<!-- Theme CSS -->
<link type="text/css" rel="stylesheet"  th:href="@{/css/theme.css}">
<!-- Demo CSS - No need to use these in your project -->
<link type="text/css"  rel="stylesheet" th:href="@{/css/demo.css}">
```

JS 链接 要用SRC

```html
<!-- Core -->
<script type="text/javascript" th:src="@{~/vendor/jquery/jquery.min.js}"></script>
<script type="text/javascript" th:src="@{~/vendor/popper/popper.min.js}" ></script>
<script type="text/javascript" th:src="@{~/js/bootstrap/bootstrap.min.js}"></script>
<!-- FontAwesome 5 -->
<script type="text/javascript" th:src="@{~/vendor/fontawesome/js/fontawesome-all.min.js}" defer></script>
<!-- Page plugins -->
<script type="text/javascript" th:src="@{~/vendor/bootstrap-select/js/bootstrap-select.min.js}"></script>
<script type="text/javascript" th:src="@{~/vendor/bootstrap-tagsinput/bootstrap-tagsinput.min.js}"></script>
<script type="text/javascript" th:src="@{~/vendor/input-mask/input-mask.min.js}"></script>
<script type="text/javascript" th:src="@{~/vendor/nouislider/js/nouislider.min.js}"></script>
<script type="text/javascript" th:src="@{~/vendor/textarea-autosize/textarea-autosize.min.js}"></script>
<!-- Theme JS -->
<script type="text/javascript" th:src="@{~/js/theme.js}"></script>
```

图片：

```html
<img th:src="@{/images/backgrounds/img-1.jpg}">
```

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/mistake2.PNG?raw=true "img1")
