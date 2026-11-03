package ma.fstt.ecommerce.servlet;

import ma.fstt.ecommerce.model.Produit;
import ma.fstt.ecommerce.service.ProduitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Contrôleur de la vitrine : une seule servlet, plusieurs actions. */
@WebServlet("/ProduitServlet")
public class ProduitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProduitService service = ServiceFactory.produitService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "lister";

        if ("lister".equals(action)) {
            request.setAttribute("produits", service.rechercher(request.getParameter("q"), toLong(request.getParameter("cat"))));
            request.setAttribute("categories", service.categories());
            forward(request, response, "vitrine.jsp");

        } else if ("detail".equals(action)) {
            request.setAttribute("produit", service.findById(toLong(request.getParameter("id"))));
            forward(request, response, "detail.jsp");

        } else if ("nouveau".equals(action)) {
            request.setAttribute("categories", service.categories());
            forward(request, response, "produit-form.jsp");

        } else if ("editer".equals(action)) {
            request.setAttribute("produit", service.findById(toLong(request.getParameter("id"))));
            request.setAttribute("categories", service.categories());
            forward(request, response, "produit-form.jsp");

        } else if ("enregistrer".equals(action)) {
            Produit p = new Produit();
            p.setId(toLong(request.getParameter("id")));
            p.setDesignation(request.getParameter("designation"));
            p.setDescription(request.getParameter("description"));
            p.setPrix(Double.parseDouble(request.getParameter("prix")));
            p.setStock(Integer.parseInt(request.getParameter("stock")));
            p.setImageUrl(request.getParameter("imageUrl"));
            p.setCategorie(service.categorie(toLong(request.getParameter("categorieId"))));
            service.enregistrer(p);
            response.sendRedirect(request.getContextPath() + "/ProduitServlet?action=lister");

        } else if ("supprimer".equals(action)) {
            try { service.supprimer(toLong(request.getParameter("id"))); }
            catch (RuntimeException e) { /* produit présent dans un panier : suppression refusée */ }
            response.sendRedirect(request.getContextPath() + "/ProduitServlet?action=lister");

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action inconnue : " + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void forward(HttpServletRequest rq, HttpServletResponse rs, String jsp) throws ServletException, IOException {
        rq.getRequestDispatcher("/WEB-INF/jsp/" + jsp).forward(rq, rs);
    }
    private Long toLong(String s) {
        try { return (s == null || s.trim().isEmpty()) ? null : Long.valueOf(s); } catch (NumberFormatException e) { return null; }
    }
}
