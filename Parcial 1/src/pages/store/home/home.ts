import type { ICategorias } from "../../../types/Categorias";
import type { Product } from "../../../types/Product";
import { logout } from "../../../utils/auth";

//Iniciamos un carrito si no existe
if (!localStorage.getItem("carrito")) {
    localStorage.setItem("carrito", JSON.stringify([]))
}

//Traemos los productos de la base de datos
const resCategorias = await fetch("/data/categorias.json");
const categorias: ICategorias[] = await resCategorias.json();

const resProductos = await fetch("/data/productos.json");
const productos: Product[] = await resProductos.json();



//Eventos de nuestra pagina web
function mostrarCategorias() {

    categorias.forEach(categoria => {
        crearCategoria(categoria);
    });


    // 🔹 Caso especial: Todos los productos
    const allProductos = document.querySelector<HTMLLIElement>("#all-products");
    allProductos?.addEventListener("click", () => {
        // Quitar activo de todas
        const todas = document.querySelectorAll("#lista-categorias li");
        todas.forEach(c => c.classList.remove("activo"));

        // Marcar solo "Todos"
        allProductos.classList.add("activo");

        // Mostrar todos los productos
        mostrarProductos(productos);
    });
};


mostrarCategorias();


function mostrarProductos(productos: Product[]) {

    const contenedorProductos = document.querySelector<HTMLDivElement>("#productos-items");

    if (contenedorProductos) {
        contenedorProductos.innerHTML = "";
    }

    // Recorremos y mostramos cada producto
    productos.forEach(productos => {
        const articulo = crearArticuloProducto(productos, categorias);
        contenedorProductos?.appendChild(articulo);
    })
}

// Mostramos todos los productos al inicio
mostrarProductos(productos);



// 🔹 Evento de búsqueda en vivo
const buscador = document.querySelector<HTMLInputElement>("#buscador");
const contenedorProductos = document.querySelector('#productos-items');

buscador?.addEventListener("input", () => {
    const texto = buscador.value.toLowerCase();

    // Filtramos productos por coincidencia en nombre
    const filtrados: Product[] = productos.filter((p: Product) => p.nombre.toLowerCase().includes(texto));

    if (filtrados.length === 0 && contenedorProductos) {
        contenedorProductos.innerHTML = "";
        const cartel = document.createElement("h2");
        cartel.textContent = "No hay coincidencias en su búsqueda";
        cartel.classList.add("mensaje");
        contenedorProductos.appendChild(cartel);
    } else {
        mostrarProductos(filtrados);
    }
})

// 🔹 Función para mostrar alerta temporal al agregar producto


// 🔹 Actualizamos el valor del carrito en la barra superior
const valorCarrito = document.querySelector<HTMLSpanElement>("#valor-carrito");

function mostrarValorCarrito() {
    const carrito = localStorage.getItem("carrito");
    const carritoProdu: Product[] = carrito ? JSON.parse(carrito) : [];
    if (valorCarrito) {
        valorCarrito.textContent = carritoProdu.length.toString();
    }
}

mostrarValorCarrito();






//Boton para salir de la sesion
const btnSesion = document.querySelector<HTMLElement>("#btn-sesion");
if (btnSesion) {
    btnSesion.addEventListener("click", () => {
        logout();
    })
}

function renderPrincipal(producto: Product) {

    const detalleBox = document.querySelector("#detalle-producto") as HTMLElement;
    const productoBox = document.querySelector("#productos-box") as HTMLElement;
    const categoriaBox = document.querySelector(".box-categoria") as HTMLElement;

    productoBox.style.display = 'none';
    categoriaBox.style.display = 'none';
    detalleBox.style.display = "flex";

    const titulo = crearElemento("h1", "", producto.nombre);
    const precioText = `Precio $${producto.precio}`
    const precio = crearElemento("p", "", precioText);

    let disponible: HTMLElement;
    if (producto.disponible) {
        disponible = crearElemento('p', "", "Disponible");
        disponible.style.backgroundColor = "green";
    } else {
        disponible = crearElemento('p', "", "No Disponible");
        disponible.style.backgroundColor = "red";
    }

    const descripcion = crearElemento('p', "", producto.descripcion);

    const imagen: HTMLImageElement = crearElemento('img') as HTMLImageElement;
    imagen.src = producto.imagen;


    //Controles - Botones
    const boxControles = crearControles();
    boxControles.classList.add("boxControles");

    const boxControlesAgregar = crearControlesAgregar();
    boxControlesAgregar.classList.add("boxAgregar")


    //Cajas de elementos
    const boxImagen = crearElemento('div', 'img-c');
    boxImagen.appendChild(imagen);

    const boxDetalle = crearElemento("div", "det-c");
    boxDetalle.append(titulo,disponible, descripcion, precio, boxControles, boxControlesAgregar);

    detalleBox.innerHTML = "";
    //Agregamos a la caja principal
    detalleBox.append(boxImagen,boxDetalle);



}


//FUnciones
function crearCategoria(categoria: ICategorias) {

    const listaCategorias = document.querySelector('#lista-categorias');

    const li = crearElemento('li', '', categoria.nombre);
    listaCategorias?.append(li);

    li?.addEventListener("click", (Event: MouseEvent) => {

        Event.preventDefault();

        // 🔹 Quitar activo de todas
        const todas = document.querySelectorAll("#lista-categorias li");
        todas.forEach(c => c.classList.remove("activo"));

        // 🔹 Marcar solo la clickeada
        li.classList.add("activo");

        // 🔹 Filtrar productos
        const contenedor = document.querySelector<HTMLDivElement>("#productos-items");
        const categoriaFiltro = categoria.nombre;

        const filtrados = productos.filter((p: Product) =>
            p.categorias.some((catId: any) => {
                const cat = categorias.find(c => c.id === catId);
                return cat?.nombre === categoriaFiltro;
            })
        );

        if (contenedor) {
            contenedor.innerHTML = "";
            mostrarProductos(filtrados);
        }
    })


}

function crearElemento(tag: string, clase?: string, texto?: string): HTMLElement {
    const elemento = document.createElement(tag);
    if (clase) elemento.classList.add(clase);
    if (texto) elemento.textContent = texto;
    return elemento;
}

//Funcion de agregar o eliminar elemento
function crearControles(): HTMLElement {
    const box = crearElemento('div', 'boxControles');
    const btnAdd = crearElemento('button', 'btnControl', '+');
    const valor = crearElemento('span', 'valor', '0');
    const btnSub = crearElemento('button', 'btnControl', '-');
    box.append(btnAdd, valor, btnSub);
    return box;
}

function crearControlesAgregar(): HTMLElement {
    const box = crearElemento('div', 'boxControlesAgregar');
    const btnAgregar = crearElemento('button', 'btnAgregar', 'Agregar a carrito');
    const btnVolver = crearElemento('button', 'btnVolver', 'Volver');
    box.append(btnAgregar, btnVolver);

    btnVolver.addEventListener("click", () => {

        const detalleBox = document.querySelector("#detalle-producto") as HTMLElement;
        const productoBox = document.querySelector("#productos-box") as HTMLElement;
        const categoriaBox = document.querySelector(".box-categoria") as HTMLElement;

        detalleBox.style.display = "none";
        productoBox.style.display = "flex";
        categoriaBox.style.display = "flex";
    })

    return box;
}


function crearArticuloProducto(producto: Product, categorias: ICategorias[]): HTMLElement {

    const articulo = crearElemento('article', 'p-articulo');
    const imagen: HTMLImageElement = crearElemento('img', 'p-imagen') as HTMLImageElement;
    imagen.src = producto.imagen;

    const boxDetalles = crearElemento('div', 'p-caracteristicas');
    const textoCategoria = producto.categorias
        .map((catId: any) => {
            const cat = categorias.find(c => c.id === catId);
            return cat?.nombre;
        })
        .join(", ")

    const categoria = crearElemento('p', 'p-categoria', textoCategoria);
    const titulo = crearElemento('h3', 'p-titulo', producto.nombre);
    const descripcion = crearElemento('p', 'p-descripcion', producto.descripcion);
    const textoPrecio = `Precio $${producto.precio}`;
    const precio = crearElemento('p', 'p-precio', textoPrecio);

    let disponible: HTMLElement;

    if (producto.disponible) {
        disponible = crearElemento('p', 'p-disponible', 'Disponible') as HTMLElement;
        disponible.style.backgroundColor = 'green';

    } else {
        disponible = crearElemento('p', 'p-disponible', 'No disponible') as HTMLElement;
        disponible.style.backgroundColor = 'red';
    }
    boxDetalles.append(categoria, titulo, descripcion, precio, disponible);
    articulo.append(imagen, boxDetalles);

    imagen.addEventListener('click', () => {
        renderPrincipal(producto);
    })

    return articulo;
}
