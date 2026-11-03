package ma.fstt.ecommerce.servlet;

import ma.fstt.ecommerce.model.Internaute;
import ma.fstt.ecommerce.service.PanierService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Contrôleur du panier. */
@WebServlet("/PanierServlet")
public class PanierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PanierService service = ServiceFactory.panierService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String ctx = request.getContextPath();
        Internaute user = (Internaute) request.getSession().getAttribute("internaute");
        if (user == null) { response.sendRedirect(ctx + "/InternauteServlet?action=formLogin"); return; }

        String action = request.getParameter("action");
        if (action == null) action = "afficher";

        if ("afficher".equals(action)) {
            request.setAttribute("panier", service.getPanier(user.getId()));
            request.getRequestDispatcher("/WEB-INF/jsp/panier.jsp").forward(request, response);

        } else if ("ajouter".equals(action)) {
            service.ajouter(user.getId(), Long.valueOf(request.getParameter("produitId")),
                    parseInt(request.getParameter("quantite"), 1));
            response.sendRedirect(ctx + "/PanierServlet?action=afficher");

        } else if ("modifier".equals(action)) {
            service.modifierQuantite(user.getId(), Long.valueOf(request.getParameter("ligneId")),
                    parseInt(request.getParameter("quantite"), 1));
            response.sendRedirect(ctx + "/PanierServlet?action=afficher");

        } else if ("retirer".equals(action)) {
            service.retirer(user.getId(), Long.valueOf(request.getParameter("ligneId")));
            response.sendRedirect(ctx + "/PanierServlet?action=afficher");

        } else if ("vider".equals(action)) {
            service.vider(user.getId());
            response.sendRedirect(ctx + "/PanierServlet?action=afficher");

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action inconnue : " + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    private int parseInt(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
}
