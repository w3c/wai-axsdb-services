/**
 * 
 */
package org.w3c.wai.accessdb.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.helpers.TestResultFilterHelper;
import org.w3c.wai.accessdb.helpers.TreeFactory;
import org.w3c.wai.accessdb.jaxb.SimpleProduct;
import org.w3c.wai.accessdb.jaxb.TechnologyCombination;
import org.w3c.wai.accessdb.jaxb.TechnologyTree;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;
import org.w3c.wai.accessdb.jaxb.TreeNodeData;
import org.w3c.wai.accessdb.om.ProductVersion;
import org.w3c.wai.accessdb.om.product.AssistiveTechnology;
import org.w3c.wai.accessdb.om.product.Platform;
import org.w3c.wai.accessdb.om.product.UAgent;

/**
 * @author evangelos.vlachogiannis
 * @since 18.06.12
 */

public enum ATService
{
    INSTANCE;
    private static final Logger logger = LoggerFactory
            .getLogger(ATService.class);

    private static List<SimpleProduct> doQuerySimpleProducts(String q)
    {
        List<Object[]> resultList = EAOManager.INSTANCE.getTestResultEAO()
                .doSimpleQuery(q);
        List<SimpleProduct> products = new ArrayList<SimpleProduct>();
        for (Object[] result : resultList)
        {
            SimpleProduct p = new SimpleProduct();
            p.setName((String) result[0]);
            p.setVersion((String) result[1]);
            products.add(p);
        }
        return products;
    }


    public TreeNodeData getATNode(TestResultFilter filter)
    {
        List<SimpleProduct> products = new ArrayList<SimpleProduct>();
        try
        {
            String q = TestResultFilterHelper.buildHQL4AT(filter);
            logger.info("getATNode Query: " + q);
            products = ATService.doQuerySimpleProducts(q);
        }
        catch (Exception e)
        {
            logger.debug(e.getLocalizedMessage());
        }
        logger.info("AT combinations from database: " + products.size());
        return TreeFactory.buildProductNode(
                AssistiveTechnology.class.getSimpleName(), products);
    }

    public TreeNodeData getUANode(TestResultFilter filter)
    {
        List<SimpleProduct> products = new ArrayList<SimpleProduct>();
        try
        {
            String q = TestResultFilterHelper.buildHQL4UA(filter);
            logger.info("getUANode Query: " + q);
            products = ATService.doQuerySimpleProducts(q);
        }
        catch (Exception e)
        {
            logger.debug(e.getLocalizedMessage());
        }
        logger.info("UA combinations from database: " + products.size());

        return TreeFactory.buildProductNode(UAgent.class.getSimpleName(),
                products);
    }

    public TreeNodeData getOSNode(TestResultFilter filter)
    {
        List<SimpleProduct> products = new ArrayList<SimpleProduct>();
        try
        {
            String q = TestResultFilterHelper.buildHQL4OS(filter);
            logger.info("getOSNode Query: " + q);
            products = ATService.doQuerySimpleProducts(q);
        }
        catch (Exception e)
        {
            logger.debug(e.getLocalizedMessage());
        }
        logger.info("OS combinations from database: " + products.size());

        return TreeFactory.buildProductNode(Platform.class.getSimpleName(),
                products);
    }

    // this is for full tree including technologies and oss
    public TechnologyTree getTechnologiesTree()
    {
        List<TechnologyCombination> combs = new ArrayList<TechnologyCombination>();
        try
        {
            combs = EAOManager.INSTANCE.getTestResultEAO()
                    .findUniqueATCombinations();
        }
        catch (Exception e)
        {
            logger.debug(e.getLocalizedMessage());
        }
        logger.info("AT combinations from database: " + combs.size());
        if (combs.isEmpty())
        {
            combs = this.getDefaultCombinations();
            logger.info("loading default combinations");
        }
        TechnologyTree tree = new TechnologyTree();
        for (TechnologyCombination comb : combs)
        {
            tree.addCombination(comb);
            logger.info("Added comb: " + comb);
        }

        return tree;
    }

    private List<TechnologyCombination> getDefaultCombinations()
    {
        List<TechnologyCombination> combs = new ArrayList<TechnologyCombination>();
        combs.add(new TechnologyCombination("JAWS", "11.9", "IE", "8.1",
                "Windows", "XP"));
        combs.add(new TechnologyCombination("JAWS", "10", "IE", "8.1",
                "Windows", "XP"));
        combs.add(new TechnologyCombination("JAWS", "10", "IE", "8.2",
                "Ubuntu", "12"));
        combs.add(new TechnologyCombination("JAWS", "11.9", "Chrome", "8.1",
                "Windows", "XP"));
        combs.add(new TechnologyCombination("NVDA", "9", "Chrome", "8.1",
                "Windows", "XP"));
        return combs;
    }

    public TreeNodeData getBrowsersTree()
    {
        return this.getProductTree("userAgent", UAgent.class.getSimpleName());
    }

    public TreeNodeData getATTree()
    {
        return this.getProductTree("assistiveTechnology",
                AssistiveTechnology.class.getSimpleName());
    }

    public TreeNodeData getOSTree()
    {
        return this.getProductTree("platform", Platform.class.getSimpleName());
    }

    public TreeNodeData getOperatingSystemsTree()
    {
        TreeNodeData rootNode = new TreeNodeData();
        List<Platform> platforms = EAOManager.INSTANCE.getPlatformEAO()
                .findAll();
        logger.info("getOperatingSystemsTree : " + platforms.size());
        for (Platform platform : platforms)
        {
            TreeNodeData node = new TreeNodeData();
            node.setType(Platform.class.getSimpleName());
            node.setId(String.valueOf(platform.getId()));
            node.setLabel(platform.getName());
            node.setDescription(platform.toString());
            node.setValue(String.valueOf(platform.getId()));
            node.setNoOfChildren(0);
            node.setSubselector(false);
            rootNode.getChildren().add(node);
        }
        return rootNode;
    }

    private TreeNodeData getProductTree(String productName, String type)
    {
        TreeNodeData rootNode = new TreeNodeData();
        String q = "select DISTINCT r.testingProfile." + productName
                + ".name from TestResult as r";
        List<String> testResults = EAOManager.INSTANCE.getObjectEAO()
                .doSimpleQuery(q);
        for (String testResult : testResults)
        {
            logger.info("getProductTree 1 Q: " + q);
            logger.info("getProductTree 1 : " + testResults.size());
            TreeNodeData nodeP = new TreeNodeData();
            nodeP.setType(UAgent.class.getSimpleName());
            nodeP.setValue(testResult);
            nodeP.setId(UAgent.class.getSimpleName() + "_" + testResult);
            nodeP.setLabel(testResult);
            q = "select DISTINCT r.testingProfile." + productName
                    + ".version from TestResult as r where r.testingProfile."
                    + productName + ".name ='" + testResult + "'";
            logger.info("getProductTree 2 Q: " + q);
            logger.info("getProductTree 2: " + testResults.size());
            List<ProductVersion> versions = EAOManager.INSTANCE.getObjectEAO()
                    .doSimpleQuery(q);
            for (ProductVersion productVersion : versions)
            {
                TreeNodeData nodeV = new TreeNodeData();
                nodeV.setType(ProductVersion.class.getSimpleName());
                nodeV.setValue(productVersion.getText());
                nodeV.setId(UAgent.class.getSimpleName() + "_" + testResult);
                nodeV.setLabel(productVersion.getText());
                nodeV.setDescription(productVersion.getText());
                nodeP.getChildren().add(nodeV);
            }
            rootNode.setNoOfChildren(nodeP.getChildren().size());
            rootNode.getChildren().add(nodeP);
        }
        return rootNode;
    }

}
