package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    // protected Item(){
    //     super();
    //     id = 0L;
    //     itemName ="";
    // }

    public Long getId() {
        return id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}

@Entity
class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;

    protected Container(){
        super();
        items = new ArrayList<>();
    }

    public int itemCount(){
        return items.size();
    }

    public void addItem(Item item) {
        items.add(item);
    }
}

public class JpaExample {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Start the transaction
            tx.begin();

            // Create and persist Item entities
            Item item1 = new Item();
            item1.setItemName("Item 1");
            em.persist(item1);
            System.out.println("Id: "+item1.getId());
            System.out.println("Name: "+item1.getItemName());

            Item item2 = new Item();
            item2.setItemName("Item 2");
            em.persist(item2);
            System.out.println("Id: "+item2.getId());
            System.out.println("Name: "+item2.getItemName());

            // Create and persist Container entity with a list of items
            Container container = new Container();
            System.out.println("Size 0: "+container.itemCount());
            container.addItem(item1);
            System.out.println("Size 0: "+container.itemCount());
            container.addItem(item2);
            System.out.println("Size 0: "+container.itemCount());
            em.persist(container);

            // Commit the transaction
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}

