package org.w3c.wai.accessdb.om.base;


import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
/**
 * @author webcc
 * @since 14.03.12
 */
@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class BaseEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = AUTO)
	private long id = -1;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object object)
    {
        boolean isEqual= false;

        if (object != null && object instanceof BaseEntity)
        {
            isEqual = (this.getId() == ((BaseEntity) object).getId());
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return (int) this.getId();
    }
	
}