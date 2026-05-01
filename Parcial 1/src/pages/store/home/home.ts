import { categorias } from "../../../data/data";
import { PRODUCTS } from "../../../data/data";
import type { Product } from "../../../types/Product";

// 🔹 Seleccionamos la lista de categorías en la barra lateral
const categoriaList = document.querySelector<HTMLUListElement>("#categoria-list");
console.log(categoriaList);

function mostrarCategorias() {
    // Recorremos todas las categorías y las mostramos en la lista lateral
    categorias.forEach(categoria => {
        const li = document.createElement("li") as HTMLElement;
        li.textContent = categoria.nombre;
        categoriaList?.appendChild(li);

        // Evento: al hacer click en una categoría, filtramos productos
        li?.addEventListener("click", (Event: MouseEvent) => {
            Event.preventDefault();

            const contenedor = document.querySelector<HTMLDivElement>("#productos");
            const categoriaFiltro = categoria.nombre;

            // Filtramos productos que tengan esa categoría (filter + some)
            const filtrados = PRODUCTS.filter(p => p.categorias.some(c => c.nombre === categoriaFiltro));
            console.log(filtrados);

            if (contenedor) {
                contenedor.innerHTML = "";
                mostrarProductos(filtrados); // mostramos solo los filtrados
            }
        })
    })
}
mostrarCategorias();

// 🔹 Evento para mostrar todos los productos sin filtro - Btn Todos los productos
const allProductos = document.querySelector<HTMLLIElement>("#all-productos");
allProductos?.addEventListener("click", () => {
    mostrarProductos(PRODUCTS);
});

// 🔹 Contenedor principal de productos
const contenedorProductos = document.querySelector<HTMLDivElement>("#productos");

function mostrarProductos(productos: Product[]) {
    // Limpiamos antes de renderizar
    if (contenedorProductos) {
        contenedorProductos.innerHTML = "";
    }

    // Recorremos y mostramos cada producto
    productos.forEach(productos => {
        const articulo = document.createElement("article");

        const imagen = document.createElement("img");
        imagen.src = productos.imagen;

        const nombre = document.createElement("h3");
        nombre.textContent = productos.nombre;

        const descripcion = document.createElement("p");
        descripcion.textContent = productos.descripcion;

        const precio = document.createElement("p");
        precio.textContent = `Precio: $ ${productos.precio}`;
        precio.classList.add("precio");

        // Botón para agregar al carrito
        const btnAgregar = document.createElement("button") as HTMLButtonElement;
        btnAgregar.textContent = "Agregar a carrito";
        btnAgregar.classList.add("agregar");

        // Armamos el artículo
        articulo.appendChild(imagen);
        articulo.appendChild(nombre);
        articulo.appendChild(descripcion);
        articulo.appendChild(precio);
        articulo.appendChild(btnAgregar);

        // Evento: al hacer click, guardamos producto en localStorage
        btnAgregar.addEventListener("click", () => {
            const carrito = localStorage.getItem("carrito");
            const carritoProductos: Product[] = carrito ? JSON.parse(carrito) : [];
            carritoProductos.push(productos);
            localStorage.setItem("carrito", JSON.stringify(carritoProductos));
            console.log("Producto agregado al carrito");
            mostrarAlerta();        // mostramos alerta temporal
            mostrarValorCarrito();  // actualizamos contador del carrito
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
