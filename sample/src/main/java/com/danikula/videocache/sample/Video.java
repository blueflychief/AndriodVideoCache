package com.danikula.videocache.sample;

public enum Video {

    //以下链接来自开眼app抓包，如有侵权，请删除
    ORANGE_1("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2178&editionType=default&source=aliyun"),
    ORANGE_2("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=20177&editionType=default&source=aliyun"),
    ORANGE_3("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=73567&editionType=default&source=aliyun"),
    ORANGE_4("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=77828&editionType=default&source=aliyun"),
    ORANGE_5("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=58846&editionType=default&source=aliyun"),
    ORANGE_6("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=71249&editionType=default&source=aliyun"),
    ORANGE_7("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=73287&editionType=default&source=aliyun"),
    ORANGE_8("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=77406&editionType=default&source=aliyun"),
    ORANGE_9("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=77827&editionType=default&source=aliyun"),
    ORANGE_10("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=72941&editionType=default&source=aliyun"),
    ORANGE_11("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=1392&editionType=default&source=aliyun");
//    ORANGE_1(Config.ROOT + "orange1.mp4"),
//    ORANGE_2(Config.ROOT + "orange2.mp4"),
//    ORANGE_3(Config.ROOT + "orange3.mp4"),
//    ORANGE_4(Config.ROOT + "orange4.mp4"),
//    ORANGE_5(Config.ROOT + "orange5.mp4");

    public final String url;

    Video(String url) {
        this.url = url;
    }

    private class Config {
        private static final String ROOT = "https://raw.githubusercontent.com/danikula/AndroidVideoCache/master/files/";
    }
}
