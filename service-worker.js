/**
 * Welcome to your Workbox-powered service worker!
 *
 * You'll need to register this file in your web app and you should
 * disable HTTP caching for this file too.
 * See https://goo.gl/nhQhGp
 *
 * The rest of the code is auto-generated. Please don't update this file
 * directly; instead, make changes to your Workbox build configuration
 * and re-run your build process.
 * See https://goo.gl/2aRDsh
 */

importScripts("https://storage.googleapis.com/workbox-cdn/releases/4.3.1/workbox-sw.js");

self.addEventListener('message', (event) => {
  if (event.data && event.data.type === 'SKIP_WAITING') {
    self.skipWaiting();
  }
});

/**
 * The workboxSW.precacheAndRoute() method efficiently caches and responds to
 * requests for URLs in the manifest.
 * See https://goo.gl/S9QRab
 */
self.__precacheManifest = [
  {
    "url": "apple-touch-icon.png",
    "revision": "875f99eac05c426fa804bb625fdfd17f"
  },
  {
    "url": "assets/css/0.styles.7d3fa5a7.css",
    "revision": "f9ce80f4b01dc3ddd32d44e3da8efbd8"
  },
  {
    "url": "assets/img/search.83621669.svg",
    "revision": "83621669651b9a3d4bf64d1a670ad856"
  },
  {
    "url": "assets/js/10.c5db6700.js",
    "revision": "aa16238ee1288def9d1fbf12a1e15b2b"
  },
  {
    "url": "assets/js/2.d5baa89d.js",
    "revision": "63cb40658ff4b7d43739dd6e903f8358"
  },
  {
    "url": "assets/js/3.12e5a52b.js",
    "revision": "2029f962154c658733babc958b7ea222"
  },
  {
    "url": "assets/js/4.1939afcd.js",
    "revision": "df4576271566f49ca1ce794fd96eba59"
  },
  {
    "url": "assets/js/5.986e4c8a.js",
    "revision": "da4358cc7c3acc046751bcd6235e5b09"
  },
  {
    "url": "assets/js/6.bf00ef84.js",
    "revision": "5548076eb7370f740589894f090c6b79"
  },
  {
    "url": "assets/js/7.df78614d.js",
    "revision": "19c74c60a123a827b40051459cf74b15"
  },
  {
    "url": "assets/js/8.464238e7.js",
    "revision": "bae8f21a18b53475f66294b3a0a9fdf5"
  },
  {
    "url": "assets/js/9.f42c78af.js",
    "revision": "6aaf6d921a907e5d5e17579a6d758d28"
  },
  {
    "url": "assets/js/app.0a5d756e.js",
    "revision": "7aca6486b64add79679934f155356024"
  },
  {
    "url": "description/index.html",
    "revision": "5681b0ee4291149c82dc0e9de62f5c99"
  },
  {
    "url": "description/OAuth2.html",
    "revision": "0af04eecafc80d70fe5303d77f1e0718"
  },
  {
    "url": "favicon-32x32.png",
    "revision": "1cd1f244c226f143b4924418a902e9bf"
  },
  {
    "url": "images/icons/icon-128x128.png",
    "revision": "b4035aed0f76f209d6ea5b8389fdddba"
  },
  {
    "url": "images/icons/icon-144x144.png",
    "revision": "435a36bd3f806957370967464aeeeb52"
  },
  {
    "url": "images/icons/icon-152x152.png",
    "revision": "7bd30061cdfcca8275235c572835c1a1"
  },
  {
    "url": "images/icons/icon-192x192.png",
    "revision": "55cdafc79f6ee8dc0e6c1a6d8a811de1"
  },
  {
    "url": "images/icons/icon-384x384.png",
    "revision": "4b5a47c3f057d846c2afc607fccc27da"
  },
  {
    "url": "images/icons/icon-512x512.png",
    "revision": "07833bce31f2b74c4aa15ca913e1d10a"
  },
  {
    "url": "images/icons/icon-72x72.png",
    "revision": "7bd46f35d797adb830109c4787616b9d"
  },
  {
    "url": "images/icons/icon-96x96.png",
    "revision": "d5cc3c671125c1a1fcbef5b094acc696"
  },
  {
    "url": "images/rbac.png",
    "revision": "447e29fd47c95bbda4881e6350bb6bd2"
  },
  {
    "url": "index.html",
    "revision": "4aa5a3cc21417e2c64cb5d4cb153252e"
  },
  {
    "url": "index.png",
    "revision": "a70a4023d560fb9e7de118e0d661b810"
  }
].concat(self.__precacheManifest || []);
workbox.precaching.precacheAndRoute(self.__precacheManifest, {});
addEventListener('message', event => {
  const replyPort = event.ports[0]
  const message = event.data
  if (replyPort && message && message.type === 'skip-waiting') {
    event.waitUntil(
      self.skipWaiting().then(
        () => replyPort.postMessage({ error: null }),
        error => replyPort.postMessage({ error })
      )
    )
  }
})
