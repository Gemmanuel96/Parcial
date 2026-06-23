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
    //const categoriaList = document.querySelector<HTMLUListElement>("#lista-categorias");

    categorias.forEach(categoria => {

        crearCategoria(categoria);
    });
};

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

mostrarCategorias();


function mostrarProductos(productos: Product[]) {

    // 🔹 Contenedor principal de productos
    const contenedorProductos = document.querySelector<HTMLDivElement>("#productos-items");

    // Limpiamos antes de renderizar
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

//Reders para productos
function renderDeDetalleProducto(producto: Product) {

    const main = document.querySelector('#main');

    if (main) {
        main.innerHTML = '';
    }

    const titulo = document.createElement('h1');
    titulo.textContent = producto.nombre;

    const precio = document.createElement('p');
    precio.textContent = `Precio: $ ${producto.precio}`;

    const disponible = document.createElement('p');
    disponible.classList.add('p-disponible')

    if (producto.disponible) {
        disponible.textContent = 'Disponible'
    } else {
        disponible.textContent = 'No disponible'
        disponible.style.backgroundColor = 'red';
    }

    const descripcion = document.createElement('p');
    descripcion.textContent = producto.descripcion;



    const imgP = document.createElement("img");
    imgP.src = producto.imagen;
    //Box Principal
    const box = document.createElement("div");
    box.classList.add("caja-p")

    //Box Contenedoras
    const c1 = document.createElement("div");
    c1.classList.add("img-c")

    const c2 = document.createElement("div");
    c2.classList.add("det-c");

    //Box de controles
    const btnBox = document.createElement("div");
    btnBox.classList.add('boxControles')

    const btnAdd = document.createElement("button");
    btnAdd.classList.add("btnControl")
    btnAdd.textContent = "+";

    const cantidadSpan = document.createElement("span");
    cantidadSpan.textContent = "0";
    cantidadSpan.classList.add("cant-span")

    const btnSubstract = document.createElement("button");
    btnAdd.classList.add("btnControl");
    btnSubstract.textContent = "-";

    btnBox.appendChild(btnAdd);
    btnBox.appendChild(cantidadSpan);
    btnBox.appendChild(btnSubstract);

    //Botones para agregar al carrito
    const btnProducto = document.createElement("div");
    btnProducto.classList.add("boxAgregar")

    const agregarCarrito = document.createElement("button");
    agregarCarrito.textContent = 'Agregar carrito';
    agregarCarrito.classList.add("btnAgregar");

    const volver = document.createElement("button");
    volver.textContent = 'Volver';
    volver.classList.add("btnVolver");

    btnProducto.appendChild(agregarCarrito);
    btnProducto.appendChild(volver);


    volver.addEventListener("click", () => {

        const main = document.querySelector('#main');

        if (main) {
            main.innerHTML = "";
        }
        mostrarProductos(productos);
        mostrarCategorias();
    })

    //Caja en donde va la imagen del producto
    c1.appendChild(imgP);

    //Caja en donde van los detalles del producto
    c2.appendChild(titulo);
    c2.appendChild(precio);
    c2.appendChild(disponible);
    c2.appendChild(descripcion);
    c2.appendChild(btnBox);
    c2.appendChild(btnProducto)

    box.appendChild(c1);
    box.appendChild(c2);

    if (main) {
        main.appendChild(box)
    }


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
    const box = crearElemento('div', 'boxAgregar');
    const btnAgregar = crearElemento('button', 'btnAgregar', 'Agregar a carrito');
    const btnVolver = crearElemento('button', 'btnVolver', 'Volver');
    box.append(btnAgregar, btnVolver);
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

    const categoria = crearElemento('p', 'p-caracteristicas', textoCategoria);
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

    imagen.addEventListener('click', ()=>{
        renderDeDetalleProducto(producto);
    })

    return articulo;
}
