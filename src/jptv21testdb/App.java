/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv21testdb;

import entity.GroupName;
import entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Melnikov
 */
public class App {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV21TestDBPU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();
    private List<Student> students;
    private List<GroupName> groups;
    private Student student=new Student();
    private GroupName groupName=new GroupName();

    public App() {
        try {
            students = em.createQuery("SELECT s FROM Student s")
                    .getResultList();
            groups = em.createQuery("SELECT g FROM GroupName g")
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Нет записей в базе");
            
        }
    }
    
    public void run(){
        tx.begin();
            if(students == null || students.isEmpty()){
                groupName.setGroupName("JPTV");
                groupName.setYear(2022);
                em.persist(groupName);
                student.setFirstname("Ivan");
                student.setLastname("Ivanov");
                student.setDay(1);
                student.setMonth(1);
                student.setYear(2000);
                student.setGroupName(groupName);
                em.persist(student);
                student.getGroupName().getStudents().add(student);
                em.merge(student);
                student = new Student();
                groupName = new GroupName();
                groupName.setGroupName("JKTV");
                groupName.setYear(2021);
                em.persist(groupName);
                student.setFirstname("Nikolay");
                student.setLastname("Petrov");
                student.setDay(2);
                student.setMonth(2);
                student.setYear(2001);
                student.setGroupName(groupName);
                em.persist(student);
                student.getGroupName().getStudents().add(student);
                em.merge(student);
            }else{
                student.setFirstname("Feodor");
                student.setLastname("Jegorov");
                student.setDay(3);
                student.setMonth(3);
                student.setYear(2003);
                student.setGroupName(groups.get(0));
                em.merge(student);
            }
        tx.commit();
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println(s.toString());
        }
        for (int i = 0; i < groups.size(); i++) {
            GroupName group = groups.get(i);
            System.out.println(group.toString());
            
        }
    }
}
