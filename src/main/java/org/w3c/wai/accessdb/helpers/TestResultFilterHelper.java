package org.w3c.wai.accessdb.helpers;

import java.util.Iterator;
import java.util.List;

import org.w3c.wai.accessdb.jaxb.SimpleProduct;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;

public class TestResultFilterHelper
{
    public static String buildHQL4TestResultViewTechnique(
            TestResultFilter filter, String techId)
    {
        if(filter.getAts().size()!=1 || filter.getUas().size()!=1 )
            return null;
        StringBuffer sql = new StringBuffer();
        SimpleProduct at = filter.getAts().get(0);
        SimpleProduct ua= filter.getUas().get(0);
        sql.append("select DISTINCT r, b.optionalName, b.user.userId, b.date from TestResult as r, TestResultsBunch as b where ");
        sql.append(" r in elements(b.results) " );
        sql.append(" and r.testUnitDescription.technique.nameId = '"+techId+"' " );
        sql.append(" and r.testingProfile.assistiveTechnology.name='"+ at.getName() +"'" );
        if(at.getVersion()!=null && at.getVersion().length()>0)
            sql.append(" and r.testingProfile.assistiveTechnology.version.text='"+ at.getVersion() +"'" );
        sql.append(" and r.testingProfile.userAgent.name='"+ ua.getName() +"'" );
        if(ua.getVersion()!=null && ua.getVersion().length()>0)
            sql.append(" and r.testingProfile.userAgent.version.text='"+ ua.getVersion() +"'" );      
        return sql.toString();
    }
    public static String buildHQL4TestResultFullViewTechnique(
            TestResultFilter filter, String techId)
    {
        StringBuffer sql = new StringBuffer();
        // get all AT combinations for filter/technique
        sql.append("select DISTINCT res.testingProfile.assistiveTechnology.name as atName, "
                + "res.testingProfile.assistiveTechnology.version.text as atVersion, "
                + "res.testingProfile.userAgent.name as uaName, "
                + "res.testingProfile.userAgent.version.text as uaVersion, "
                + "res.testingProfile.platform.name as osName, "
                + "res.testingProfile.platform.version.text as osVersion from TestResult as res "
                + "where res.testUnitDescription.technique.nameId = '" + techId + "'");
        // filter
        if (filter.getAts().size() > 0)
        {
            sql.append(" AND ");
            sql.append(getTestingProfileSubQuery(filter.getAts(),
                    "assistiveTechnology","res"));
        }
        if (filter.getUas().size() > 0)
        {           
            sql.append(" AND ");
            sql.append(getTestingProfileSubQuery(filter.getUas(), "userAgent","res"));
        }
        if (filter.getOss().size() > 0)
        {           
            sql.append(" AND ");
            sql.append(getTestingProfileSubQuery(filter.getOss(), "platform","res"));
        }
        return sql.toString();
    }

    public static String buildHQL4TestResultsOverviewAll(
            TestResultFilter filter, String techId)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(DISTINCT res) from TestResult as res, TestUnitDescription as tu where ");
        sql.append("tu.testUnitId=res.testUnitDescription.testUnitId and tu.technique.nameId = '"
                + techId + "' ");
        sql.append(" and res in ("
                + TestResultFilterHelper.buildHQL4TestResults(filter) + ")");
        return sql.toString();
    }

    public static String buildHQL4TestResultsOverviewPass(
            TestResultFilter filter, String techId)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(DISTINCT res) from TestResult as res, TestUnitDescription as tu where ");
        sql.append("tu.testUnitId=res.testUnitDescription.testUnitId and res.resultValue=true and tu.technique.nameId = '"
                + techId + "' ");
        sql.append(" and res in ("
                + TestResultFilterHelper.buildHQL4TestResults(filter) + ")");
        return sql.toString();
    }

    public static String buildHQL4TestResults(TestResultFilter filter)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select DISTINCT r from TestResult as r ");
        if (filter.getAts().size() > 0 || filter.getOss().size() > 0
                || filter.getUas().size() > 0)
            sql.append(" where ");
        sql.append(getTestingProfileSubQuery(filter.getAts(),
                "assistiveTechnology","r"));
        if (filter.getAts().size() > 0
                && (filter.getUas().size() > 0 || filter.getOss().size() > 0))
            sql.append(" AND ");
        sql.append(getTestingProfileSubQuery(filter.getUas(), "userAgent","r"));
        if (filter.getUas().size() > 0 && filter.getOss().size() > 0)
            sql.append(" AND ");
        sql.append(getTestingProfileSubQuery(filter.getOss(), "platform","r"));
        return sql.toString();

    }
    

    public static String buildHQL4Technique(TestResultFilter filter)
    {
        StringBuffer sql = new StringBuffer();
        // select all techniques based on filter having min test runs..
        sql.append("select DISTINCT t  from Technique as t, SuccessCriterio as sc where ");
        sql.append(" t in elements(sc.techniques) ");
        if (filter.getTechnologies().size() > 0)
            sql.append(" and t.webTechnology.nameId in "
                    + list2InSqlString(filter.getTechnologies()));
        if (filter.getCriterios().size() > 0)
            sql.append(" and sc.number in "
                    + list2InSqlString(filter.getCriterios()));
        return sql.toString();
    }

    //
    public static String buildHQL4AT(TestResultFilter filter)
    {
        StringBuffer sql = new StringBuffer();
        boolean where = false;
        sql.append("select DISTINCT  r.testingProfile.assistiveTechnology.name as name, "
                + "r.testingProfile.assistiveTechnology.version.text as version from TestResult as r ");
        if (filter.getOss().size() > 0 || filter.getUas().size() > 0)
        {
            sql.append(" WHERE ");
            where = true;
        }
        sql.append(getTestingProfileSubQuery(filter.getOss(), "platform","r"));
        if (!where && filter.getOss().size() > 0)
            sql.append(" WHERE ");
        if (where && filter.getOss().size() > 0 && filter.getUas().size() > 0)
            sql.append(" AND ");
        sql.append(getTestingProfileSubQuery(filter.getUas(), "userAgent","r"));
        sql.append(" ORDER BY r.testingProfile.assistiveTechnology.name, r.testingProfile.assistiveTechnology.version.text");
        return sql.toString().trim();
    }

    public static String buildHQL4UA(TestResultFilter filter)
    {
        StringBuffer sql = new StringBuffer();
        boolean where = false;
        sql.append("select DISTINCT r.testingProfile.userAgent.name as name, "
                + "r.testingProfile.userAgent.version.text as version from TestResult as r ");
        if (filter.getOss().size() > 0 || filter.getAts().size() > 0)
        {
            sql.append(" WHERE ");
            where = true;
        }
        sql.append(getTestingProfileSubQuery(filter.getOss(), "platform","r"));
        if (!where && filter.getAts().size() > 0)
            sql.append(" WHERE ");
        if (where && filter.getAts().size() > 0 && filter.getUas().size() > 0)
            sql.append(" AND ");
        sql.append(getTestingProfileSubQuery(filter.getAts(),
                "assistiveTechnology","r"));
        sql.append(" ORDER BY r.testingProfile.userAgent.name, r.testingProfile.userAgent.version.text");
        return sql.toString().trim();
    }

    public static String buildHQL4OS(TestResultFilter filter)
    {
        StringBuffer sql = new StringBuffer();
        boolean where = false;
        sql.append("select DISTINCT r.testingProfile.platform.name as name, "
                + "r.testingProfile.platform.version.text as version from TestResult as r ");
        if (filter.getUas().size() > 0 || filter.getAts().size() > 0)
        {
            sql.append(" WHERE ");
            where = true;
        }
        sql.append(getTestingProfileSubQuery(filter.getUas(), "userAgent","r"));
        if (!where && filter.getAts().size() > 0)
            sql.append(" WHERE ");
        if (where && filter.getAts().size() > 0 && filter.getUas().size() > 0)
            sql.append(" AND ");
        sql.append(getTestingProfileSubQuery(filter.getAts(),
                "assistiveTechnology","r"));
        sql.append(" ORDER BY r.testingProfile.platform.name, r.testingProfile.platform.version.text");
        return sql.toString().trim();
    }

    private static String getTestingProfileSubQuery(
            List<SimpleProduct> products, String type, String resAlias)
    {
        StringBuffer sql = new StringBuffer();
        if (products.size() > 0)
        {
            Iterator<SimpleProduct> iter = products.iterator();
            while (iter.hasNext())
            {
                SimpleProduct at = iter.next();
                sql.append(" ( ");
                sql.append(" " + resAlias+"."+ "testingProfile." + type + ".name='" + at.getName() + "'");
                
                if (at.getVersion()!=null && at.getVersion().length() > 0)
                {
                    sql.append(" AND " + resAlias+"."+ "testingProfile." + type
                            + ".version.text='" + at.getVersion() + "'");
                    
                }
                sql.append(" ) ");
                if (iter.hasNext())
                { 
                    sql.append(" OR ");
                }                    
            }
        }
        return sql.toString();
    }

    public static String list2InSqlString(List<String> list)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" (");
        for (String element : list)
        {
            if (sql.length() == 2)
                sql.append("'").append(element).append("'");
            else
                sql.append(",'").append(element).append("'");
        }
        sql.append(") ");
        return sql.toString();
    }
}
