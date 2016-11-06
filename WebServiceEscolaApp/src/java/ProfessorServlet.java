/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ''''VINI
 */
@WebServlet(urlPatterns = {"/ProfessorServlet"})
public class ProfessorServlet extends HttpServlet {
    
    String dbUrl="jdbc:mysql://localhost:3306/escolaapp";
    String username="root";
    String password="";

    /**
     * Processejdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull [root em Esquema default]s requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println();
        System.out.println(request.getParameter("senha"));
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(dbUrl,username,password);
            System.out.println("Connected!");
            
            
            
            PreparedStatement findstatement = conn.prepareStatement("SELECT * FROM professor WHERE cpf=? AND senha=?");
            findstatement.setString(1, request.getParameter("cpf"));
            findstatement.setString(2, request.getParameter("senha"));
            
            ResultSet rs = findstatement.executeQuery();
            int tamanhoRs = 0;
            Professor prof = new Professor();
            while (rs.next()){
                
                prof.id=rs.getInt("_id");
                prof.nome=rs.getString("nome");
                prof.cpf=rs.getString("cpf");
                prof.senha=rs.getString("senha");            
                
                tamanhoRs++;
                break;
            }
            findstatement.close();
            
            response.setContentType("application/json;charset=UTF-8");
            
            if(tamanhoRs==1){
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    
                    out.println("[{id:\""+prof.id+"\","
                            + "nome:\""+prof.nome+"\""
                            + "}]");
                }
            }else{
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    
                    out.println("[{id:\"-1\","
                            + "nome:\"\""
                            + "}]");
                }
            }

           } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("not connected");

           } catch(ClassNotFoundException x){
            x.printStackTrace();

           } catch(Exception e){

            e.printStackTrace();
           }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
