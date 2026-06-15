import { categorias } from "../../../data/data";
import { PRODUCTS } from "../../../data/data";
import type { Product } from "../../../types/Product";


// 🔹 Seleccionamos la lista de categorías en la barra lateral


function mostrarCategorias() {
    const categoriaList = document.querySelector<HTMLUListElement>("#lista-categorias");

    categorias.forEach(categoria => {
        const li = document.createElement("li");
        li.textContent = categoria.nombre;
        categoriaList?.appendChild(li);

        li.addEventListener("click", (event: MouseEvent) => {
            event.preventDefault();

            // 🔹 Quitar activo de todas
            const todas = document.querySelectorAll("#lista-categorias li");
            todas.forEach(c => c.classList.remove("activo"));

            // 🔹 Marcar solo la clickeada
            li.classList.add("activo");

            // 🔹 Filtrar productos
            const contenedor = document.querySelector<HTMLDivElement>("#productos-items");
            const categoriaFiltro = categoria.nombre;
            const filtrados = PRODUCTS.filter(p =>
                p.categorias.some(c => c.nombre === categoriaFiltro)
            );

            if (contenedor) {
                contenedor.innerHTML = "";
                mostrarProductos(filtrados);
            }
        });
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
        mostrarProductos(PRODUCTS);
    });
}

mostrarCategorias();


// 🔹 Evento para mostrar todos los productos sin filtro - Btn Todos los productos
//const allProductos = document.querySelector<HTMLLIElement>("#all-products");
//allProductos?.addEventListener("click", () => {
//  allProductos.classList.add("activo");
//mostrarProductos(PRODUCTS);
//});


// 🔹 Contenedor principal de productos
const contenedorProductos = document.querySelector<HTMLDivElement>("#productos-items");

function mostrarProductos(productos: Product[]) {
    // Limpiamos antes de renderizar
    if (contenedorProductos) {
        contenedorProductos.innerHTML = "";
    }

    // Recorremos y mostramos cada producto
    productos.forEach(productos => {
        const articulo = document.createElement("article");
        articulo.classList.add("p-articulo");

        const imagen = document.createElement("img");
        imagen.classList.add("p-imagen");
        imagen.src = productos.imagen;

        const pDetalles = document.createElement("div")
        pDetalles.classList.add("p-caracteristica");

        //Elementos de detalle
        const categoria = document.createElement("p");
        categoria.classList.add("p-categoria");
        categoria.textContent = productos.categorias.map(cat => cat.nombre).join(",");

        const titulo = document.createElement("h3");
        titulo.classList.add("p-titulo")
        titulo.textContent = productos.nombre;

        const descripcion = document.createElement("p");
        descripcion.classList.add("p-descripcion")
        descripcion.textContent = productos.descripcion;

        const precio = document.createElement("p");
        precio.textContent = `Precio: $ ${productos.precio}`;
        precio.classList.add("p-precio");

        const disponible = document.createElement("p");
        disponible.classList.add("p-disponible");

        if (productos.disponible == true) {
            disponible.textContent = "Disponible";
        } else {
            disponible.textContent = "No disponible";
        }

        pDetalles.appendChild(categoria);
        pDetalles.appendChild(titulo);
        pDetalles.appendChild(descripcion);
        pDetalles.appendChild(precio);
        pDetalles.appendChild(disponible);

        articulo.appendChild(imagen);
        articulo.appendChild(pDetalles);

        articulo.addEventListener("click", (event: MouseEvent) =>{

            
        })

        contenedorProductos?.appendChild(articulo);
    })
}

// Mostramos todos los productos al inicio
mostrarProductos(PRODUCTS);

// 🔹 Evento de búsqueda en vivo
const buscador = document.querySelector<HTMLInputElement>("#buscador");
buscador?.addEventListener("input", () => {
    const texto = buscador.value.toLowerCase();

    // Filtramos productos por coincidencia en nombre
    const filtrados = PRODUCTS.filter(p => p.nombre.toLowerCase().includes(texto));

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
function mostrarAlerta() {
    const alerta = document.querySelector<HTMLDivElement>("#alerta");
    if (alerta) {
        alerta.style.display = "flex";
        setTimeout(() => {
            alerta.style.display = "none";
        }, 2000);
    }
}

// 🔹 Actualizamos el valor del carrito en la barra superior
const valorCarrito = document.querySelector<HTMLElement>("#valor-carrito");
function mostrarValorCarrito() {
    const carrito = localStorage.getItem("carrito");
    const carritoProdu: Product[] = carrito ? JSON.parse(carrito) : [];
    if (valorCarrito) {
        valorCarrito.textContent = carritoProdu.length.toString();
    }
}
mostrarValorCarrito();
