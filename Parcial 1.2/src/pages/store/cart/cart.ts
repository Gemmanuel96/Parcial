import type { Product } from "../../../types/Product";

const carrito = localStorage.getItem("carrito");
const productoCarrito: Product[] = carrito ? JSON.parse(carrito) : [];
//Contenedor de donde van los productos del carrito
const productosBox = document.querySelector<HTMLElement>("#productos-box");
console.log(productosBox);


const agrupados: Product[] = Object.values(
    productoCarrito.reduce((acc: any, prod: Product) => {
        if (!acc[prod.id]) {
            acc[prod.id] = { ...prod, cantidad: 0 };
        } else {
            acc[prod.id].cantidad += 1;
        }
        return acc;
    }, {})
);





function mostrarCarrito() {
    agrupados.forEach(producto => {
        //Contenedores de producto
        const productoItem = document.createElement("div");
        productoItem.classList.add("producto-item")

        const detalle = document.createElement("div");
        detalle.classList.add("detalle-item")

        const controles = document.createElement("div");
        controles.classList.add("controles-item");

        //Controlador de Productos
        // Botón -
        const btnMenos = document.createElement("button");
        btnMenos.textContent = "-";

        // Contador
        const cantidad = document.createElement("span");
        cantidad.textContent = contadorProductos(producto.id).toString();

        // Botón +
        const btnMas = document.createElement("button");
        btnMas.textContent = "+";

        controles.appendChild(btnMenos);
        controles.appendChild(cantidad);
        controles.appendChild(btnMas);


        const imagen = document.createElement("img");
        imagen.src = producto.imagen

        productoItem.appendChild(imagen);

        const nombre = document.createElement("h3");
        nombre.textContent = producto.nombre;

        const descripcion = document.createElement("p");
        descripcion.textContent = producto.descripcion;


        detalle.appendChild(nombre);
        detalle.appendChild(descripcion);

        productoItem.appendChild(detalle);
        productoItem.appendChild(controles);

        productosBox?.appendChild(productoItem);


        btnMas.addEventListener("click", () => {
            agregarProducto(producto);
            cantidad.textContent = contadorProductos(producto.id).toString();
            console.log("sumando")
        });

        btnMenos.addEventListener("click", () => {
            eliminarProducto(producto);
            cantidad.textContent = contadorProductos(producto.id).toString();
        });
    })
}

mostrarCarrito();


//Funciones para los botones
function agregarProducto(prod: Product) {
    productoCarrito.push(prod);
    localStorage.setItem("carrito", JSON.stringify(productoCarrito));
}

function eliminarProducto(prod: Product) {
    const index = productoCarrito.findIndex(p => p.id === prod.id);
    if (index !== -1) {
        productoCarrito.splice(index, 1);
        localStorage.setItem("carrito", JSON.stringify(productoCarrito));
    }
}

//Nos devolvera el total de cada producto
function contadorProductos(id: number): number {
    return productoCarrito.filter(p => p.id === id).length;
}