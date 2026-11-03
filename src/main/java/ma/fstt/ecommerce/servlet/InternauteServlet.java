package ma.fstt.ecommerce.servlet;

import ma.fstt.ecommerce.model.Internaute;
import ma.fstt.ecommerce.service.InternauteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/** Contrôleur de la gestion des internautes (inscription, connexion, profil). */
@WebServlet("/InternauteServlet")
public class InternauteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final InternauteService service = ServiceFactory.internauteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "formLogin";
        String ctx = request.getContextPath();
        HttpSession session = request.getSession();
        Internaute courant = (Internaute) session.getAttribute("internaute");

        if ("formLogin".equals(action)) {
            forward(request, response, "login.jsp");

        } else if ("connecter".equals(action)) {
            Internaute i = service.authentifier(request.getParameter("email"), request.getParameter("motDePasse"));
            if (i == null) {
                request.setAttribute("erreur", "Email ou mot de passe incorrect.");
                forward(request, response, "login.jsp");
            } else {
                session.setAttribute("internaute", i);
                response.sendRedirect(ctx + "/ProduitServlet?action=lister");
            }

        } else if ("formInscription".equals(action)) {
            forward(request, response, "inscription.jsp");

        } else if ("inscrire".equals(action)) {
            Internaute i = new Internaute(request.getParameter("nom"), request.getParameter("prenom"),
                    request.getParameter("email"), request.getParameter("motDePasse"), request.getParameter("adresse"));
            if (service.inscrire(i) == null) {
                request.setAttribute("erreur", "Cet email est déjà utilisé.");
                forward(request, response, "inscription.jsp");
            } else {
                response.sendRedirect(ctx + "/InternauteServlet?action=formLogin");
            }

        } else if ("deconnecter".equals(action)) {
            session.invalidate();
            response.sendRedirect(ctx + "/ProduitServlet?action=lister");

        } else if ("profil".equals(action)) {
            if (courant == null) { response.sendRedirect(ctx + "/InternauteServlet?action=formLogin"); return; }
            forward(request, response, "profil.jsp");

        } else if ("modifier".equals(action)) {
            if (courant == null) { response.sendRedirect(ctx + "/InternauteServlet?action=formLogin"); return; }
            Internaute i = service.findById(courant.getId());
            i.setNom(request.getParameter("nom"));
            i.setPrenom(request.getParameter("prenom"));
            i.setAdresse(request.getParameter("adresse"));
            session.setAttribute("internaute", service.modifier(i));
            response.sendRedirect(ctx + "/InternauteServlet?action=profil");

        } else if ("supprimer".equals(action)) {
            if (courant != null) {
                try { service.supprimer(courant.getId()); session.invalidate(); }
                catch (RuntimeException e) { /* compte lié à un panier : suppression refusée */ }
            }
            response.sendRedirect(ctx + "/ProduitServlet?action=lister");

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
}
