package com.think.linxuanxuan.sampleapplication.other;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * DefaultHandler是SAX专门用来处理xml文件的
 */
public class XML2Product extends DefaultHandler {

    private List<Product> products;
    private Product product;
    private StringBuffer buffer = new StringBuffer();

    public List<Product> getProducts() {
        return products;
    }

    /**
     * 开始解析文档
     */
    @Override
    public void startDocument() throws SAXException {
        products = new ArrayList<>();
    }

    /**
     * 开始解析节点,凡有这个标签<>的都是节点
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Log.d("tag", uri + "," + localName + "," + qName);
        if (localName.equals("product")) {
            //如果开始解析的是<product>标签，则创建一个product对象
            product = new Product();
        }
        super.startElement(uri, localName, qName, attributes);
    }

    /**
     * 保存节点文档内容
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        Log.d("tag", ch + "," + start + "," + length);
        buffer.append(ch, start, length);
        super.characters(ch, start, length);
    }

    /**
     * 结束解析节点
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("product")) {
            products.add(product);
        } else if (localName.equals("id")) {
            product.id = Integer.parseInt(buffer.toString().trim());
        } else if (localName.equals("name")) {
            product.name = buffer.toString().trim();
        } else if (localName.equals("price")) {
            product.price = Float.parseFloat(buffer.toString().trim());
        }
        //将buffer中的内容清空
        buffer.setLength(0);
        super.endElement(uri, localName, qName);
    }

}
