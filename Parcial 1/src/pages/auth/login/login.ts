import type { IUser } from "../../../types/IUser";
import { saveUser } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";

const form = document.querySelector<HTMLFormElement>("#formulario-login");

form?.addEventListener("submit", (event: SubmitEvent) => {

  event.preventDefault();

  //Traemos los datos del formulario
  const formElement = event.currentTarget as HTMLFormElement;
  const formData = new FormData(formElement);

  //Capturamos los datos de los inputs
  const inputImail = formData.get("email") as string;
  const inputContrasenia = formData.get("contrasenia") as string;

  //Traemos los datos de localStorage
  const usuariosGuardados = localStorage.getItem("users");
  const usuarios: IUser[] = usuariosGuardados ? JSON.parse(usuariosGuardados) : [];

  const usuarioEncontrado = usuarios.find((u: IUser) => u.email === inputImail && u.contrasenia === inputContrasenia);

  //Si encuentra el usuario, verificamos el rol y lo redireccionara a la pagina principal de usuario o admin

  if (usuarioEncontrado) {
    saveUser(usuarioEncontrado);


    if(usuarioEncontrado.rol === "admin"){
      navigate("/src/pages/admin/home/home.html");
      alert("Iniciando sesion");

    }else if(usuarioEncontrado.rol ==="client"){
      navigate("/src/pages/store/home/home.html");
      alert("Iniciando sesion")
    } 

  }else{
    alert("Email o contraseña incorrecta")!
    formElement.reset;
  }
  
});
