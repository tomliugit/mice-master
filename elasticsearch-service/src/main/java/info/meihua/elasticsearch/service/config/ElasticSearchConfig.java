package info.meihua.elasticsearch.service.config;

import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunwell
 */
@Configuration
public class ElasticSearchConfig {

    private JestClient client;

    public JestClient getClient() {
        return client;
    }

    @Autowired(required = false)
    public ElasticSearchConfig(@Value("${elasticsearch-config.host}") String esUrl) {
        client = getClientConfig(esUrl);
    }

    public JestClient getClientConfig(String esUrl) {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(esUrl)
                .gson(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create())
                .multiThreaded(true)
                .connTimeout(60000)
                .readTimeout(60000)
                .build());
        return factory.getObject();
    }

}