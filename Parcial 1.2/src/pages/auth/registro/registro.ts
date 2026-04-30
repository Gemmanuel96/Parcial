import { navigate } from "../../../utils/navigate";
import type { IUser } from "../../../types/IUser";
import type { Rol } from "../../../types/Rol";



const form = document.getElementById("formulario-login") as HTMLFormElement;

form?.addEventListener("submit",(event: SubmitEvent) =>{

    //Hacemos que la pagina no se refresque
    event.preventDefault();

    //Traemos los datos del formulario
    const formElement = event.currentTarget as HTMLFormElement;
    const formData = new FormData(formElement);

    //Creamos un nuevo Usuario y guardamos los datos
    const usuarioNuevo : IUser = {
        nombreCompleto: formData.get("nombre") as string,
        email: formData.get("email") as string,
        contrasenia: formData.get("contrasenia") as string,
        rol: formData.get("rol") as Rol

    };

    //Traemos los datos que hay en el storage con la clave 'users'
    const usuariosGuardados = localStorage.getItem("users");
    const usuarios: IUser [] = usuariosGuardados ? JSON.parse(usuariosGuardados) : [];

    //Agregamos al usuario nuevo
    usuarios.push(usuarioNuevo); 

    //Guardamos en el Array actualizado en el localStorage
    localStorage.setItem("users",JSON.stringify(usuarios));

    console.log("Usuario registrado: " + usuarioNuevo.nombreCompleto);

    //reiniciamos los valores del formulario
    formElement.reset();

    
    setTimeout(() => {
        alert("Usuario registrado!");
        navigate("/src/pages/auth/login/login.html");
    },1000);
});