package com.codegym.controller;

import com.codegym.model.Classroom;
import com.codegym.model.Student;
import com.codegym.service.ClassroomService;
import com.codegym.service.ClassroomServiceImpl;
import com.codegym.service.StudentService;
import com.codegym.service.StudentServiceImpl;
import com.codegym.utils.Validate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "HomepageServlet",urlPatterns = "/homepage")
public class HomepageServlet extends HttpServlet {
    ClassroomService classroomService;
    StudentService studentService;

    @Override
    public void init() throws ServletException {
        classroomService = new ClassroomServiceImpl();
        studentService = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create" :
                ShowCreateStudent(req,resp);
                break;
            case "edit" :
                ShowEditStudent(req,resp);
                break;
            case "delete":
                deleteStudent(req, resp);
                break;
            default:
                listStudent(req,resp);
                break;
        }
    }

    private void ShowCreateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("create.jsp");
        List<Classroom> classroomList = classroomService.findAll();
        req.setAttribute("classroomList",classroomList);
        dispatcher.forward(req,resp);
    }

    private void ShowEditStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("edit.jsp");
        String strId = req.getParameter("id");
        boolean idIsNumber = Validate.isNumberValid(strId);
        List<String> errors = new ArrayList<>();
        if (!idIsNumber) {
            errors.add("Id Kh??ng H???p L???");
        }else {
            boolean existsId = studentService.exists(Integer.parseInt(strId));
            if (!existsId) {
                errors.add("Id Kh??ng T???n T???i");
            }
        }
        if (errors.size() == 0) {
            List<Student> studentList = studentService.findById(Integer.parseInt(strId));
            req.setAttribute("studentList",studentList);
        }
        if (errors.size() > 0 ){
            req.setAttribute("errors",errors);
        }
        List<Classroom> classroomList = classroomService.findAll();
        req.setAttribute("classroomList",classroomList);
        dispatcher.forward(req,resp);
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("list.jsp");
        String id = req.getParameter("id");
        List<String> errors = new ArrayList<>();
        boolean idIsNumber = Validate.isNumberValid(id);
        if (!idIsNumber) {
            errors.add("Id Kh??ng H???p L???");
        }else {
            boolean existsId = studentService.exists(Integer.parseInt(id));
            if (!existsId) {
                errors.add("Id Kh??ng T???n T???i");
            }else {
                if (errors.size() == 0 ){
                    boolean success = studentService.delete(Integer.parseInt(id));
                    if (success) {
                        req.setAttribute("success","X??a Th??nh C??ng");
                    }else {
                        errors.add("X??a Kh??ng Th??nh C??ng");
                    }
                }
            }
        }
        if (errors.size() > 0 ){
            req.setAttribute("errors",errors);
        }
        List<Student> studentList = studentService.findAll();
        List<Classroom> classroomList = classroomService.findAll();
        req.setAttribute("studentList",studentList);
        req.setAttribute("classroomList",classroomList);
        dispatcher.forward(req,resp);
    }

    private void listStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("list.jsp");
        List<Classroom> classroomList = classroomService.findAll();
        List<Student> studentList = studentService.findAll();

        req.setAttribute("classroomList",classroomList);
        req.setAttribute("studentList",studentList);


        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create" :
                createStudent(req,resp);
                break;
            case "edit" :
                editStudent(req,resp);
                break;
            case "search":
                searchStudent(req,resp);
            default:
                listStudent(req,resp);
                break;
        }
    }

    private void searchStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("list.jsp");
        String key = req.getParameter("search");
        List<Student> studentList = studentService.search(key);
        List<Classroom> classroomList = classroomService.findAll();
        req.setAttribute("classroomList",classroomList);
        req.setAttribute("studentList",studentList);
        dispatcher.forward(req,resp);
    }

    private void createStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("create.jsp");
        String name = req.getParameter("student_name");
        String email = req.getParameter("email");
        String strdob = req.getParameter("dob");
        String address = req.getParameter("address");
        String strPhone = req.getParameter("phone_number");
        String strClassId = req.getParameter("class_id");
        List<Classroom> classroomList = classroomService.findAll();
        req.setAttribute("classroomList",classroomList);
        List<String> errors = new ArrayList<>();

        if (name.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng T??n");
        }
        if (email.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng Email");
        }
        if (strdob.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng Ng??y Sinh");
        }
        if (address.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng ?????a Ch???");
        }
        if (strPhone.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng S??? ??i???n Tho???i");
        }
        if(strClassId.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng L???p");
        }

        if (name.length() < 3) {
            errors.add("T??n Kh??ng H???p L???");
        }

        boolean checkEmail = Validate.isEmail(email);
        if (!checkEmail) {
            errors.add("Email Kh??ng H???p L???");
        }

        boolean checkPhone = Validate.isPhone(strPhone);
        if (!checkPhone) {
            errors.add("S??? ??i???n Tho???i Kh??ng H???p L???");
        }

        boolean checkDate = Validate.isDateValid(strdob);
        if (!checkDate) {
            errors.add("Ng??y Sinh Kh??ng H???p L???");
        }else {
            boolean idClassIsNumber = Validate.isNumberValid(strClassId);
            if (!idClassIsNumber) {
                errors.add("ID L???p H???c Kh??ng H???p L???");
            }

            boolean existsIdClass = classroomService.exists(Integer.parseInt(strClassId));
            if (!existsIdClass) {
                errors.add("L???p H???c Kh??ng T???n T???i");
            }
            if (errors.size() == 0) {
                Student student = new Student(name, LocalDate.parse(strdob),address,strPhone,email,Integer.parseInt(strClassId));
                System.out.println(strdob);
                boolean createStudent = studentService.create(student);
                if (!createStudent) {
                    errors.add("Th??m H???c Sinh M???i Th???t B???i");
                }else {
                    req.setAttribute("success","Th??m H???c Sinh M???i Th??nh C??ng");
                }
            }
        }
        if (errors.size() > 0) {
            req.setAttribute("errors",errors);
        }
        dispatcher.forward(req,resp);

    }

    private void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("edit.jsp");
        String id = req.getParameter("id");
        String name = req.getParameter("student_name");
        String email = req.getParameter("email");
        String strdob = req.getParameter("dob");
        String address = req.getParameter("address");
        String strPhone = req.getParameter("phone_number");
        String strClassId = req.getParameter("class_id");
        List<String> errors = new ArrayList<>();

        if (name.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng T??n");
        }
        if (email.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng Email");
        }
        if (strdob.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng Ng??y Sinh");
        }
        if (address.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng ?????a Ch???");
        }
        if (strPhone.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng S??? ??i???n Tho???i");
        }
        if(strClassId.equals("")) {
            errors.add("Kh??ng ???????c ????? Tr???ng L???p");
        }

        if (name.length() < 3) {
            errors.add("T??n Kh??ng H???p L???");
        }

        boolean checkEmail = Validate.isEmail(email);
        if (!checkEmail) {
            errors.add("Email Kh??ng H???p L???");
        }

        boolean checkPhone = Validate.isPhone(strPhone);
        if (!checkPhone) {
            errors.add("S??? ??i???n Tho???i Kh??ng H???p L???");
        }

        boolean checkDate = Validate.isDateValid(strdob);
        if (!checkDate) {
            errors.add("Ng??y Sinh Kh??ng H???p L???");
        }else {
            boolean idClassIsNumber = Validate.isNumberValid(strClassId);
            if (!idClassIsNumber) {
                errors.add("ID L???p H???c Kh??ng H???p L???");
            }else {
                boolean existsIdClass = classroomService.exists(Integer.parseInt(strClassId));
                if (!existsIdClass) {
                    errors.add("L???p H???c Kh??ng T???n T???i");
                }else {
                    boolean idIsNumber = Validate.isNumberValid(id);
                    if (!idIsNumber) {
                        errors.add("Id H???c Sinh Kh??ng H???p L???");
                    }else {
                        boolean existsIdStudent = studentService.exists(Integer.parseInt(id));
                        if (!existsIdStudent) {
                            errors.add("Id H???c Sinh Kh??ng T???n T???i");
                        }else {
                            if (errors.size() == 0) {
                                Student student = new Student(Integer.parseInt(id),name, LocalDate.parse(strdob),address,strPhone,email,Integer.parseInt(strClassId));
                                System.out.println(strdob);
                                boolean editStudent = studentService.update(student);
                                if (!editStudent) {
                                    errors.add("S???a Th??ng Tin H???c Sinh  Th???t B???i");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (errors.size() == 0) {
            req.setAttribute("success","S???a Th??nh C??ng");
        }
        if (errors.size() > 0) {
            req.setAttribute("errors",errors);
        }
        List<Classroom> classroomList = classroomService.findAll();
        List<Student> studentList = studentService.findById(Integer.parseInt(id));
        req.setAttribute("studentList",studentList);
        req.setAttribute("classroomList",classroomList);
        dispatcher.forward(req,resp);
    }
}
