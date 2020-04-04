module.exports = {
    title: '授权服务器',
    description: '文档说明',
    port: 1111,
    base: '/authorization-server/',
    head: [
        ['link', {rel: 'apple-touch-icon', href: '/apple-touch-icon.png'}],
        ['link', {rel: 'icon', href: '/favicon-32x32.png'}],
        ['link', {rel: 'manifest', href: '/manifest.json'}],
        ['meta', {name: 'theme-color', content: '#ffffff'}]
    ],
    plugins: {
        '@vuepress/pwa': {
            serviceWorker: true,
            updatePopup: {
                message: '内容已经更新啦～',
                buttonText: '跟我走！'
            }
        },
        '@vuepress/back-to-top': true
    },
    themeConfig: {
        repo: 'gzmuSoft/authorization-server',
        editLinkText: '帮助我们改进此文档！',
        editLinks: true,
        docsDir: 'docs',
        nav: [
            {text: '主页', link: '/'},
            {text: '操作手册', link: '/description/'},
            {text: '技术架构', link: '/technology/'}
        ],
        sidebar: {
            '/description/': [
                ['', '文档说明'],
                ['/description/OAuth2', 'OAuth2']
            ]
        },
        sidebarDepth: 2,
        lastUpdated: 'Last Updated'
    }
}