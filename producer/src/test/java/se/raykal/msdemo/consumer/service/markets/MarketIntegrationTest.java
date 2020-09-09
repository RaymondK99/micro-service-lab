package se.raykal.msdemo.consumer.service.markets;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import se.raykal.msdemo.consumer.service.KafkaSender;
import se.raykal.msdemo.consumer.service.markets.BinanceIntegration;
import se.raykal.msdemo.model.OrderBookUpdate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
		"markets.kucoin.currencylist=CHR",
		"markets.binance.currencylist=CHR",
		"markets.bithumb.currencylist=CHR",

})
class MarketIntegrationTest {

	@Autowired
	private BinanceIntegration binanceIntegration;

	@Autowired
	private KuCoinIntegration kuCoinIntegration;

	@Autowired
	private BitHumbIntegration bitHumbIntegration;


	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private KafkaSender kafkaSender;

	@MockBean
	private ExchangeRatesApiIntegration exchangeRatesApiIntegration;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testBinanceIntegration() {
		ArgumentCaptor<OrderBookUpdate> param = ArgumentCaptor.forClass(OrderBookUpdate.class);
		//verify(kafkaSender).sendMessageOnDefaultTopic(param.capture());
		doNothing().when(kafkaSender).sendOrderBookUpdate(param.capture());

		String json = "{\"symbol\":\"CHRUSDT\",\"bidPrice\":\"0.05280000\",\"bidQty\":\"86693.40000000\",\"askPrice\":\"0.05290000\",\"askQty\":\"7361.60000000\"}";
		when(restTemplate.exchange(
				ArgumentMatchers.anyString(),
				ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())
		).thenReturn(
				new ResponseEntity<>(json, HttpStatus.OK)
		);

		// Call service class
		binanceIntegration.produceNewPrice();

		// Verify values
		OrderBookUpdate update = param.getValue();

		assertEquals(update.getBidPrice().doubleValue(),0.05280000);
		assertEquals(update.getAskPrice().doubleValue(),0.05290000);

	}

	@Test
	void testKuCoinIntegration() {
		ArgumentCaptor<OrderBookUpdate> param = ArgumentCaptor.forClass(OrderBookUpdate.class);
		//verify(kafkaSender).sendMessageOnDefaultTopic(param.capture());
		doNothing().when(kafkaSender).sendOrderBookUpdate(param.capture());

		String json = "{\"code\":\"200000\",\"data\":{\"time\":1599470428791,\"sequence\":\"1594226383945\",\"price\":\"0.054216\",\"size\":\"1374.8721\",\"bestBid\":\"0.054087\",\"bestBidSize\":\"4684\",\"bestAsk\":\"0.054216\",\"bestAskSize\":\"11265.5804\"}}\n";
		when(restTemplate.exchange(
				ArgumentMatchers.anyString(),
				ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())
		).thenReturn(new ResponseEntity<>(json, HttpStatus.OK));

		// Call service class
		kuCoinIntegration.produceNewPrice();

		// Verify values
		OrderBookUpdate update = param.getValue();

		assertEquals(update.getBidPrice().doubleValue(),0.054087);
		assertEquals(update.getAskPrice().doubleValue(),0.054216);

	}

	@Test
	void testBitHumbIntegration() {
		ArgumentCaptor<OrderBookUpdate> param = ArgumentCaptor.forClass(OrderBookUpdate.class);
		//verify(kafkaSender).sendMessageOnDefaultTopic(param.capture());
		doNothing().when(kafkaSender).sendOrderBookUpdate(param.capture());

		String json = "{\n" +
				"    \"data\": {\n" +
				"        \"asks\": [\n" +
				"            {\n" +
				"                \"price\": \"62.23\",\n" +
				"                \"quantity\": \"399.6528\"\n" +
				"            }\n" +
				"        ],\n" +
				"        \"bids\": [\n" +
				"            {\n" +
				"                \"price\": \"61.31\",\n" +
				"                \"quantity\": \"401.9136\"\n" +
				"            }\n" +
				"        ],\n" +
				"        \"order_currency\": \"CHR\",\n" +
				"        \"payment_currency\": \"KRW\",\n" +
				"        \"timestamp\": \"1599488701659\"\n" +
				"    },\n" +
				"    \"status\": \"0000\"\n" +
				"}";

		// Mock rest call
		when(restTemplate.exchange(
				ArgumentMatchers.anyString(),
				ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())
		).thenReturn(new ResponseEntity<>(json, HttpStatus.OK));

		// Mock call to convert KRW -> USD
		when(this.exchangeRatesApiIntegration.getExchangeRateAgainstUSD(anyString())).thenReturn(1189.7666525244);

		// Call service class
		bitHumbIntegration.produceNewPrice();

		// Verify values
		OrderBookUpdate update = param.getValue();

		assertEquals(update.getBidPrice().doubleValue(),0.051531);
		assertEquals(update.getAskPrice().doubleValue(),0.052304);

	}

}
